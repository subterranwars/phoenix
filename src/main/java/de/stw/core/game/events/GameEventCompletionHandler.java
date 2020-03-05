package de.stw.core.game.events;

import de.stw.core.buildings.GameEvent;

public interface GameEventCompletionHandler<T extends GameEvent> {
    void handle(T gameEvent);
}
