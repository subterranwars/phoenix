package de.stw.phoenix.game.events;

import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.Tick;

public interface GameEvent {
    Moment getCompletionMoment();

    // TODO MVR maybe should be provided somewhere else
    default boolean isComplete(Tick currentTick) {
        return currentTick.toMoment().isGreaterOrEqual(getCompletionMoment());
    }
}
