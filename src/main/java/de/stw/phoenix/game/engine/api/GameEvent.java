package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.Tick;

public interface GameEvent {

    /**
     * The moment this event is completed
     *
     * @return The moment this event is completed.
     */
    Moment getCompletionMoment();

    /**
     * In some cases it is required to show a different completion moment to the user
     * than when the event is actually done.
     *
     * For this purpose the {@link #getUserCompletionMoment()} can be used.
     * @return The {@link Moment} the user thinks the event is completed.
     */
    default Moment getUserCompletionMoment() {
        return getCompletionMoment();
    }

    // TODO MVR maybe should be provided somewhere else
    default boolean isCompleted(Tick currentTick) {
        return currentTick.toMoment().isGreaterOrEqual(getCompletionMoment());
    }

    <T> T accept(EventVisitor<T> visitor);
}
