package de.stw.phoenix.game.engine.modules;

import de.stw.phoenix.game.engine.api.EventVisitor;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.modules.completion.ConstructionEventPlayerUpdate;
import de.stw.phoenix.game.engine.modules.completion.ResourceSearchEventPlayerUpdate;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompletionModule implements GameModule {

    @Override
    public List<PlayerUpdate> getPlayerUpdates(ImmutablePlayer player, Tick currentTick) {
        final EventVisitor<PlayerUpdate> visitor = new EventVisitor<PlayerUpdate>() {

            @Override
            public PlayerUpdate visit(ConstructionEvent constructionEvent) {
                return new ConstructionEventPlayerUpdate(constructionEvent);
            }

            @Override
            public PlayerUpdate visit(ResourceSearchEvent resourceSearchEvent) {
                return new ResourceSearchEventPlayerUpdate(resourceSearchEvent);
            }
        };
        final List<PlayerUpdate> playerUpdates = player.getEvents().stream()
                .filter(e -> e.isCompleted(currentTick))
                .map(e -> e.accept(visitor))
                .collect(Collectors.toList());
        return playerUpdates;
    }

}
