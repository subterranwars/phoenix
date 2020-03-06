package de.stw.phoenix.game.events;

public interface GameEventCompletionHandler<T extends GameEvent> {
    void handle(T gameEvent);
}
