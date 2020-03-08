package de.stw.phoenix.auth.api;

import java.time.Instant;
import java.util.Objects;

public class Token {
    private final String token;
    private final Instant expireTime;

    public Token(String token, Instant expireTime) {
        this.token = Objects.requireNonNull(token);
        this.expireTime = Objects.requireNonNull(expireTime);
    }

    public String getToken() {
        return token;
    }

    public Instant getExpireTime() {
        return expireTime;
    }
}
