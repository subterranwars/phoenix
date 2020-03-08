package de.stw.phoenix.game.events;

import com.google.common.collect.Maps;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.data.buildings.Buildings;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.engine.modules.construction.ConstructionEvent;
import de.stw.phoenix.game.engine.modules.construction.ConstructionInfo;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GameEventCompletionModule implements GameModule {

    private static final GameEventCompletionHandler NOOP_HANDLER = new GameEventCompletionHandler() {
        @Override
        public void handle(GameEvent gameEvent) {
            LoggerFactory.getLogger(getClass()).warn("GameEvent of type {} is completed, but no handler for this type was found. Ignoring completion.", gameEvent.getClass());
        }
    };

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    private Map<Class<? extends GameEvent>, GameEventCompletionHandler<? extends GameEvent>> handlers = Maps.newHashMap();

    @PostConstruct
    public void init() {
        handlers.put(ConstructionEvent.class, (GameEventCompletionHandler<ConstructionEvent>) gameEvent -> {
            ConstructionInfo constructionInfo = gameEvent.getConstructionInfo();
            LoggerFactory.getLogger(getClass()).info("Completing construction event. User: {}, Building: {}, Level: {}", gameEvent.getPlayerRef().getName(), gameEvent.getConstructionInfo().getBuilding().getLabel(), gameEvent.getConstructionInfo().getLevelToBuild());
            final BuildingLevel newLevel = new BuildingLevel(Buildings.findByRef(constructionInfo.getBuilding()), constructionInfo.getLevelToBuild());
            playerAccessor.modify(gameEvent.getPlayerRef(), mutablePlayer -> mutablePlayer.setBuilding(newLevel));
        });
    }

    @Override
    public void update(MutablePlayer player, Tick tick) {
        final List<GameEvent> completedEvents = player.getEvents(tick);
        for (GameEvent eachEvent : completedEvents) {
            complete(eachEvent);
        }
        player.removeEvents(completedEvents);
    }

    @Override
    public void afterUpdate(MutablePlayer player, Tick tick) {

    }

    private <T extends GameEvent> void complete(T completedEvent) {
        Objects.requireNonNull(completedEvent);
        if (handlers.containsKey(completedEvent.getClass())) {
            ((GameEventCompletionHandler<T>) handlers.get(completedEvent.getClass())).handle(completedEvent);
        } else {
            NOOP_HANDLER.handle(completedEvent);
        }
    }
}
