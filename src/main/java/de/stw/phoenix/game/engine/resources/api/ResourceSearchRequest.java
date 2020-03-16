package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;

public class ResourceSearchRequest {
    private final Resource resource;
    private final PlayerRef playerRef;

    public ResourceSearchRequest(PlayerRef playerRef, Resource resourceId) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.resource = Objects.requireNonNull(resourceId);
    }

    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    public Resource getResource() {
        return resource;
    }
}
