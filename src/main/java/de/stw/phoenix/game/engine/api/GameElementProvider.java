package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;

public interface GameElementProvider {
    void registerElements(final MutableContext context, final ImmutablePlayer player);
}
