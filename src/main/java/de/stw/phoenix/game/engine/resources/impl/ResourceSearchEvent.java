package de.stw.phoenix.game.engine.resources.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.time.Moment;

import java.util.Objects;

public class ResourceSearchEvent implements GameEvent {

    @JsonIgnore
    private final PlayerRef playerRef;
    private final Resource resource;
    private final Moment lastUpdate;
    private final Progress progress;

    public ResourceSearchEvent(PlayerRef playerRef, Resource resource, Moment lastUpdate) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.resource = Objects.requireNonNull(resource);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
        this.progress = Progress.builder().withUnknownDuration().build();
    }

    @Override
    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }

    @Override
    public Moment getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public boolean isFinished() {
        return progress.isFinished();
    }

    public Resource getResource() {
        return resource;
    }

}
