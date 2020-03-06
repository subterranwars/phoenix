package de.stw.phoenix.auth.api;

import de.stw.phoenix.user.api.User;

import java.util.Objects;
import java.util.Optional;

public interface AuthService {
    Token authenticate(String username, String password);

    void invalidate(Token token);

    Optional<User> findUser(String token);

    default Token authenticate(UserAuthRequest authRequest) {
        Objects.requireNonNull(authRequest);
        return this.authenticate(authRequest.getUsername(), authRequest.getPassword());
    }
}
