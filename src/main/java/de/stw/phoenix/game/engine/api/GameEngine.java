package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;

public interface GameEngine {
    void loop();

    Context getContext(ImmutablePlayer player);
}
