package de.stw.core.game.events;

import com.google.common.collect.Maps;
import de.stw.core.buildings.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@Service
public class GameEventCompletionService {

    private static final GameEventCompletionHandler NOOP_HANDLER = new GameEventCompletionHandler() {
        @Override
        public void handle(GameEvent gameEvent) {
            LoggerFactory.getLogger(getClass()).warn("GameEvent of type {} is completed, but no handler for this type was found. Ignoring completion.", gameEvent.getClass());
        }
    };

    private Map<Class<? extends GameEvent>, GameEventCompletionHandler<? extends GameEvent>> handlers = Maps.newHashMap();

    @PostConstruct
    public void init() {
        handlers.put(ConstructionEvent.class, (GameEventCompletionHandler<ConstructionEvent>) gameEvent -> {
            ConstructionInfo constructionInfo = gameEvent.getConstructionInfo();
            LoggerFactory.getLogger(getClass()).info("Completing construction event. User: {}, Building: {}, Level: {}", gameEvent.getUser().getName(), gameEvent.getConstructionInfo().getBuilding().getLabel(), gameEvent.getConstructionInfo().getLevelToBuild());
            final BuildingLevel newLevel = new BuildingLevel(Buildings.findByRef(constructionInfo.getBuilding()), constructionInfo.getLevelToBuild());
            gameEvent.getUser().setBuilding(newLevel);
        });
    }

    public <T extends GameEvent> void complete(T completedEvent) {
        Objects.requireNonNull(completedEvent);
        if (handlers.containsKey(completedEvent.getClass())) {
            ((GameEventCompletionHandler<T>) handlers.get(completedEvent.getClass())).handle(completedEvent);
        } else {
            NOOP_HANDLER.handle(completedEvent);
        }
    }


}
