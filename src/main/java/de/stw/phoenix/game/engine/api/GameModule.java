package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.player.api.MutablePlayer;

public interface GameModule {

    void update(MutablePlayer player, Tick tick);

    void afterUpdate(MutablePlayer player, Tick tick);
}
