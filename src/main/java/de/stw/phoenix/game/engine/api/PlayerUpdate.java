package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;

public interface PlayerUpdate extends GameElement {

    int getPhase();

    default void preUpdate(MutablePlayer player, Tick tick) {}

    void update(MutablePlayer player, Tick tick);

    default void postUpdate(MutablePlayer player, Tick tick) {}
}
