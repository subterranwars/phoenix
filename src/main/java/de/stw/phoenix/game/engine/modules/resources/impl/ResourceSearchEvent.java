package de.stw.phoenix.game.engine.modules.resources.impl;

import de.stw.phoenix.game.engine.api.GameBehaviour;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSite;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourceSearchEvent implements GameEvent {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final ResourceSearchInfo info;
    private final Moment completionMoment;

    public ResourceSearchEvent(ResourceSearchInfo info, Moment completionMoment) {
        this.info = Objects.requireNonNull(info);
        this.completionMoment = Objects.requireNonNull(completionMoment);
    }

    @Override
    public Moment getCompletionMoment() {
        return info.getSuccessMoment();
    }

    @Override
    public Moment getUserCompletionMoment() {
        return completionMoment;
    }

    public ResourceSearchInfo getInfo() {
        return info;
    }

    @Override
    public GameBehaviour getBehaviour() {
        return new GameBehaviour() {
            @Override
            public void update(MutablePlayer player, Tick tick) {
                if (isCompleted(tick)) {
                    final long amount = (long) (Math.random() * 100000);
                    LoggerFactory.getLogger(getClass()).info("Completing resource search event. User: {}, Resource: {}, Success: {}, Amount: {}", player.getName(), getInfo().getResource(), getInfo().isSuccess(), amount);
                    if (getInfo().isSuccess()) {
                        final ResourceSite resourceSite = new ResourceSite(
                                ID_GENERATOR.getAndIncrement(),
                                new ImmutableResourceStorage(getInfo().getResource(), amount, amount), 0);
                        player.addResourceSite(resourceSite);
                    }
                }

            }
        };
    }
}
