package de.stw.rest.dto;

import de.stw.core.buildings.BuildingRef;
import de.stw.core.buildings.ConstructionInfo;

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
