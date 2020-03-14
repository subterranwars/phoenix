package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.Tick;

public interface GameElement {
    boolean isActive(ImmutablePlayer player, Tick currentTick);
}
