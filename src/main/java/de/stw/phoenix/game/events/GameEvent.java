package de.stw.phoenix.game.events;

import de.stw.phoenix.game.clock.Tick;

public interface GameEvent {
    Tick getCompletionTick();

    // TODO MVR maybe should be provided somewhere else
    default boolean isComplete(Tick currentTick) {
        return currentTick.isGreaterOrEqual(getCompletionTick());
    }
}
