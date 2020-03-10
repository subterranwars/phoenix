package de.stw.phoenix.game.rest.construction;

import de.stw.phoenix.game.engine.buildings.BuildingRef;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.GameEventTypes;

public class ConstructionEventDTO extends GameEventDTO {

    private BuildingRef building;
    private int level;

    public ConstructionEventDTO(long completedInSeconds, ConstructionInfo constructionInfo) {
        super(GameEventTypes.Construction, completedInSeconds);
        this.building = constructionInfo.getBuilding();
        this.level = constructionInfo.getLevelToBuild();
    }

    public BuildingRef getBuilding() {
        return building;
    }

    public int getLevel() {
        return level;
    }
}
