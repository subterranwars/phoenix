package de.stw.phoenix.game.engine.requirements;

import de.stw.phoenix.game.player.impl.Player;

public interface Requirement {
    boolean fulfills(Player player);
}
