package de.stw.phoenix.game.engine.provider.completion;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.api.events.EventVisitor;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletionUpdateProvider implements GameElementProvider {

    @Autowired
    private EventBus eventBus;

    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        final EventVisitor<PlayerUpdate> visitor = new EventVisitor<PlayerUpdate>() {
            @Override
            public PlayerUpdate visit(ConstructionEvent constructionEvent) {
                return new ConstructionEventPlayerUpdate(eventBus, constructionEvent);
            }

            @Override
            public PlayerUpdate visit(ResourceSearchEvent resourceSearchEvent) {
                return new ResourceSearchEventPlayerUpdate(eventBus, resourceSearchEvent);
            }
        };
        player.getEvents().stream().map(e -> e.accept(visitor)).forEach(context::add);
    }
}
