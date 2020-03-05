package de.stw.rest.dto;

import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.BuildingRef;

public class ConstructionEventDTO extends GameEventDTO {

    private BuildingRef building;
    private int level;

    public ConstructionEventDTO(long completedInSeconds, BuildingLevel buildingLevel) {
        super(GameEventTypes.Construction, completedInSeconds);
        this.building = buildingLevel.getBuilding();
        this.level = buildingLevel.getLevel();
    }

    public BuildingRef getBuilding() {
        return building;
    }

    public int getLevel() {
        return level;
    }
}
