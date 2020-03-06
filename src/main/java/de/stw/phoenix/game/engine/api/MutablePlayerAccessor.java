package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;
import java.util.function.Consumer;

public interface MutablePlayerAccessor {

    default void requestAccess(PlayerRef player, Consumer<MutablePlayer> consumer) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(consumer);
        this.requestAccess(player.getName(), consumer);
    }

    void requestAccess(String playerName, Consumer<MutablePlayer> consumer);
}
