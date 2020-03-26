package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.player.impl.Player;

import java.util.Objects;

public class ResourceSearchRequest {
    private final Resource resource;
    private final Player player;

    public ResourceSearchRequest(Player player, Resource resourceId) {
        this.player = Objects.requireNonNull(player);
        this.resource = Objects.requireNonNull(resourceId);
    }

    public Player getPlayer() {
        return player;
    }

    public Resource getResource() {
        return resource;
    }
}
