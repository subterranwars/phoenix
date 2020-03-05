package de.stw.core.buildings;

import de.stw.core.clock.Tick;

public interface GameEvent {
    Tick getCompletionTick();

    // TODO MVR maybe should be provided somewhere else
    default boolean isComplete(Tick currentTick) {
        return currentTick.isGreaterOrEqual(getCompletionTick());
    }
}
