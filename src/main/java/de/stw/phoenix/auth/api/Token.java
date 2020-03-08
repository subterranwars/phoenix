package de.stw.phoenix.auth.api;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Token {
    private final String token;
    private final Instant creationTime;
    private final Duration duration;

    public Token(String token, Instant creationTime, Duration duration) {
        this.token = Objects.requireNonNull(token);
        this.creationTime = Objects.requireNonNull(creationTime);
        this.duration = Objects.requireNonNull(duration);
    }

    public String getToken() {
        return token;
    }

    public Instant getExpireTime() {
        return creationTime.plus(duration);
    }

    public boolean isValid(Instant current) {
        return current.isBefore(getExpireTime()) || current.equals(getExpireTime());
    }

    public Duration getDuration() {
        return duration;
    }

    public Instant getCreationTime() {
        return creationTime;
    }
}
