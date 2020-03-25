package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Moment;

public interface GameEvent {

    Moment getLastUpdate();
    Progress getProgress();

    Player getPlayer();

    boolean isFinished();

    // TODO MVR do we need this?
    <T> T accept(EventVisitor<T> visitor);
}
