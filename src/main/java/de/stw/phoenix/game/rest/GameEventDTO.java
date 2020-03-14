package de.stw.phoenix.game.rest;


import de.stw.phoenix.game.player.api.Progress;

import java.util.Objects;

public abstract class GameEventDTO {

    private Progress progress;

    private String type;

    public GameEventDTO(String type, Progress progress) {
        this.type = Objects.requireNonNull(type);
        this.progress = Objects.requireNonNull(progress);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Progress getProgress() {
        return progress;
    }
}
