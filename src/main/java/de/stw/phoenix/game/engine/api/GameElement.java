package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;

public interface GameElement {
    boolean isActive(Player player, Tick currentTick);
}
