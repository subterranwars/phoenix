package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;

public interface GameBehaviour {

    void update(MutablePlayer player, Tick tick);
}
