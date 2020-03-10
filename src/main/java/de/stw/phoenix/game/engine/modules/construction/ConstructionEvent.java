package de.stw.phoenix.game.engine.modules.construction;

import de.stw.phoenix.game.data.buildings.Buildings;
import de.stw.phoenix.game.engine.api.GameBehaviour;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
    private final ConstructionInfo constructionInfo;
    private final Moment completionMoment;

    public ConstructionEvent(ConstructionInfo constructionInfo, Moment completionMoment) {
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionMoment = Objects.requireNonNull(completionMoment);
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }

    @Override
    public Moment getCompletionMoment() {
        return completionMoment;
    }

    @Override
    public GameBehaviour getBehaviour() {
        return new GameBehaviour() {
            @Override
            public void update(MutablePlayer player, Tick tick) {
                if (isCompleted(tick)) {
                    ConstructionInfo constructionInfo = getConstructionInfo();
                    LoggerFactory.getLogger(getClass()).info("Completing construction event. User: {}, Building: {}, Level: {}", player.getName(), constructionInfo.getBuilding().getLabel(), constructionInfo.getLevelToBuild());
                    final BuildingLevel newLevel = new BuildingLevel(Buildings.findByRef(constructionInfo.getBuilding()), constructionInfo.getLevelToBuild());
                    player.setBuilding(newLevel);
                }
            }
        };
    }
}
