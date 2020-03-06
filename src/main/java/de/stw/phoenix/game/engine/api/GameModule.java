package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

public interface GameModule {

    void update(ImmutablePlayer player, Tick tick);

    void afterUpdate(ImmutablePlayer player, Tick tick);
}
