package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.impl.Player;

public interface GameEngine {
    void loop();

    Context getContext(Player player);
}
