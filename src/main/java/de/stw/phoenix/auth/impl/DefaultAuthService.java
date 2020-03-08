package de.stw.phoenix.auth.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import de.stw.phoenix.auth.api.AuthService;
import de.stw.phoenix.auth.api.Token;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;

    // username => token
    private final Map<String, Token> userTokenMap = Maps.newConcurrentMap();

    // token => username
    private final Map<String, String> tokenUserMap = Maps.newConcurrentMap();
    private final Duration tokenExpiration;

    @Autowired
    public DefaultAuthService(final UserRepository userRepository,
                              @Value("${de.subterranwars.auth.token.expirationInHours}") int expirationInHours) {
        this(userRepository, Duration.ofHours(expirationInHours));
    }

    @VisibleForTesting
    protected DefaultAuthService(UserRepository userRepository, Duration tokenExpiration) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.tokenExpiration = Objects.requireNonNull(tokenExpiration);
    }

    @Override
    public Token authenticate(String username, String password) {
        final Optional<User> userOptional = userRepository.lookup(username, password);
        if (userOptional.isPresent()) {
            // Ensure already existing tokens are invalidated
            findToken(userOptional.get().getUsername())
                    .ifPresent(token -> invalidate(token));

            // Create new token
            final String token = UUID.randomUUID().toString();
            final Token newToken = new Token(token, Instant.now(), tokenExpiration);
            userTokenMap.put(username, newToken);
            tokenUserMap.put(token, username);
            return newToken;
        }
        throw new BadCredentialsException("Username or password incorrect");
    }

    @Override
    public void invalidate(Token token) {
        final String user = tokenUserMap.remove(token.getToken());
        userTokenMap.remove(user);
    }

    @Override
    public Optional<User> findAuthenticatedUser(final String tokenString) {
        final Optional<User> user = userTokenMap.values().stream()
                .filter(t -> Objects.equals(t.getToken(), tokenString))
                .findAny()
                .map(token -> {
                    // Ensure that when fetching the token it is still valid
                    if (!token.isValid(Instant.now())) {
                        invalidate(token); // invalidate if no longer valid
                    }
                    // token is valid, fetch the User
                    return Optional.ofNullable(tokenUserMap.get(tokenString))
                            .flatMap(username -> userRepository.find(username));
                }).orElse(Optional.empty());
        return user;
    }

    @VisibleForTesting
    protected Optional<Token> findToken(String username) {
        return Optional.ofNullable(userTokenMap.get(username));
    }

}
