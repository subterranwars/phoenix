package de.stw.core.buildings;

import de.stw.core.clock.Tick;

public interface GameEvent {
    Tick getCompletionTick();

    boolean isComplete(Tick currentTick);
}
