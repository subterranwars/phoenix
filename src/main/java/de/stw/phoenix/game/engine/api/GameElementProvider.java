package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.impl.Player;

public interface GameElementProvider {
    void registerElements(final MutableContext context, final Player player);
}