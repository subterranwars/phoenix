package de.stw.phoenix.game.engine.provider.completion;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class ResourceSearchEventPlayerUpdate extends AbstractPlayerUpdateEvent<ResourceSearchEvent> {

    // TODO MVR this may clashes if the user already has sites and we load this, then 1 is already assigned.... need a better more secure way
    // of creating ids (maybe use hibernate?)
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(10);

    public ResourceSearchEventPlayerUpdate(EventBus eventBus, ResourceSearchEvent event) {
        super(eventBus, event);
    }

    @Override
    public void update(MutablePlayer player, Tick tick) {
        final long amount = (long) (Math.random() * 100000);
        final ResourceSearchInfo info = event.getInfo();
        LoggerFactory.getLogger(getClass()).info("Completing resource search event. User: {}, Resource: {}, Success: {}, Amount: {}", player.getName(), info.getResource().getName(), info.isSuccess(), amount);
        if (info.isSuccess()) {
            final ResourceSite resourceSite = new ResourceSite(
                    ID_GENERATOR.getAndIncrement(),
                    new ImmutableResourceStorage(info.getResource(), amount, amount), 0);
            player.addResourceSite(resourceSite);
        }
    }
}
