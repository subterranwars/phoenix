package de.stw.phoenix.game.rest;

import java.util.Objects;

public abstract class GameEventDTO {

    private long completedInSeconds;

    private String type;

    public GameEventDTO(String type, long completedInSeconds) {
        this.type = Objects.requireNonNull(type);
        this.completedInSeconds = completedInSeconds;
    }

    public long getCompletedInSeconds() {
        return completedInSeconds;
    }

    public void setCompletedInSeconds(long completedInSeconds) {
        this.completedInSeconds = completedInSeconds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
