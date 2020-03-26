package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;

public interface PlayerUpdate extends GameElement {

    int getPhase();

    void update(Player player, Tick tick);

}
