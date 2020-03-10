package de.stw.phoenix.game.engine.modules.completion;

import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GameEventCompletionModule implements GameModule {

    @PostConstruct
    public void init() {
    }

    @Override
    public void update(MutablePlayer player, Tick tick) {
        final List<GameEvent> completedEvents = player.getEvents(tick);
        for (GameEvent eachEvent : completedEvents) {
            eachEvent.getBehaviour().update(player, tick);
        }
        player.removeEvents(completedEvents);
    }

    @Override
    public void afterUpdate(MutablePlayer player, Tick tick) {

    }
}
