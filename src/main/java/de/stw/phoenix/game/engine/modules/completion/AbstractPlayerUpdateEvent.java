package de.stw.phoenix.game.engine.modules.completion;

import de.stw.phoenix.game.engine.api.GameEvent;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;

import java.util.Objects;

public abstract class AbstractPlayerUpdateEvent<T extends GameEvent> implements PlayerUpdate {
    protected final T event;

    public AbstractPlayerUpdateEvent(T event) {
        this.event = Objects.requireNonNull(event);
    }

    @Override
    public void postUpdate(MutablePlayer player, Tick tick) {
        player.removeEvent(event);
    }
}
