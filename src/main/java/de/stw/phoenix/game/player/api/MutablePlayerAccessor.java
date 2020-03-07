package de.stw.phoenix.game.player.api;

import java.util.Objects;
import java.util.function.Consumer;

public interface MutablePlayerAccessor {

    default void modify(PlayerRef player, Consumer<MutablePlayer> consumer) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(consumer);
        this.modify(player.getName(), consumer);
    }

    void modify(String playerName, Consumer<MutablePlayer> consumer);
}
