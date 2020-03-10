package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.Tick;

import java.util.List;

public interface GameModule {
    List<PlayerUpdate> getPlayerUpdates(ImmutablePlayer player, Tick currentTick);
}
