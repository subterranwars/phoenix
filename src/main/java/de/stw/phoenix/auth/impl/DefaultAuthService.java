package de.stw.phoenix.auth.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import de.stw.phoenix.auth.api.AuthService;
import de.stw.phoenix.auth.api.Token;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final Map<String, Token> userTokenMap = Maps.newConcurrentMap();
    private final Map<String, String> tokenUserMap = Maps.newConcurrentMap();

    @Autowired
    public DefaultAuthService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    // TODO MVR implement properly
    @Override
    public Token authenticate(String username, String password) {
        final Optional<User> userOptional = userRepository.find(username);
        if (userOptional.isPresent()) {
            findToken(userOptional.get().getUsername())
                    .ifPresent(token -> invalidate(token));
            // TODO MVR hash password
            if (password.equals(userOptional.get().getPassword())) {
                final String token = UUID.randomUUID().toString();
                // TODO dynamic expiration (maybe make configurable)
                final Token newToken = new Token(token, Instant.now().plus(Duration.ofDays(1)));
                userTokenMap.put(username, newToken);
                tokenUserMap.put(token, username);
                return newToken;
            }
        }
        throw new BadCredentialsException("Username or password incorrect");
    }

    @Override
    public void invalidate(Token token) {
        final String user = tokenUserMap.remove(token.getToken());
        userTokenMap.remove(user);
    }

    @Override
    public Optional<User> findUser(String token) {
        Optional<User> user = Optional.ofNullable(tokenUserMap.get(token))
                .flatMap(username -> userRepository.find(username));
        return user;
    }

    @VisibleForTesting
    protected Optional<Token> findToken(String username) {
        return Optional.ofNullable(userTokenMap.get(username));
    }

}
