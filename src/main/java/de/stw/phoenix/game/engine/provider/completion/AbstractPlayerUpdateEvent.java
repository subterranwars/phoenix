package de.stw.phoenix.game.engine.provider.completion;

import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;

import java.util.Objects;

public abstract class AbstractPlayerUpdateEvent<T extends GameEvent> implements PlayerUpdate {
    protected final T event;

    public AbstractPlayerUpdateEvent(T event) {
        this.event = Objects.requireNonNull(event);
    }

    @Override
    public int getPhase() {
        return Phases.FinishEvents;
    }

    @Override
    public void postUpdate(MutablePlayer player, Tick tick) {
        player.removeEvent(event);
    }

    @Override
    public boolean isActive(ImmutablePlayer player, Tick currentTick) {
        return event.isCompleted(currentTick);
    }
}
