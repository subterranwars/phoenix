package de.stw.phoenix.game.engine.provider.completion;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.LoggerFactory;

public class ConstructionEventPlayerUpdate extends AbstractPlayerUpdateEvent<ConstructionEvent> {

    public ConstructionEventPlayerUpdate(EventBus eventBus, ConstructionEvent event) {
        super(eventBus, event);
    }

    @Override
    protected void updateInternal(MutablePlayer player, Tick tick) {
        final ConstructionInfo constructionInfo = event.getConstructionInfo();
        LoggerFactory.getLogger(getClass()).info("Completing construction event. User: {}, Building: {}, Level: {}", player.getName(), constructionInfo.getBuilding().getLabel(), constructionInfo.getLevelToBuild());
        final Building building = Buildings.findByRef(constructionInfo.getBuilding());
        final BuildingLevel newLevel = new BuildingLevel(building, constructionInfo.getLevelToBuild());
        player.setBuilding(newLevel);

        // TODO MVR is this really how we want to do things?
        eventBus.post(event);
    }
}
