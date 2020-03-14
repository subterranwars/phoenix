package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.time.Moment;

public interface GameEvent {

    Moment getLastUpdate();
    Progress getProgress();

    PlayerRef getPlayerRef();

    boolean isFinished();

    // TODO MVR do we need this?
    <T> T accept(EventVisitor<T> visitor);
}
