package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public class ResourceSearchRequest {
    private final TimeDuration duration;
    private final Resource resource;
    private final PlayerRef playerRef;

    public ResourceSearchRequest(PlayerRef playerRef, Resource resourceId, TimeDuration duration) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.resource = Objects.requireNonNull(resourceId);
        this.duration = Objects.requireNonNull(duration);
    }

    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    public TimeDuration getDuration() {
        return duration;
    }

    public Resource getResource() {
        return resource;
    }
}
