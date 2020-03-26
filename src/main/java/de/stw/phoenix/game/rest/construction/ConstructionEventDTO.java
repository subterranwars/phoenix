package de.stw.phoenix.game.rest.construction;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.BuildingRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.GameEventTypes;

public class ConstructionEventDTO extends GameEventDTO {

    private BuildingRef building;
    private int level;

    public ConstructionEventDTO(Progress progress, Building building, int levelToBuild) {
        super(GameEventTypes.Construction, progress);
        this.building = building;
        this.level = levelToBuild;
    }

    public BuildingRef getBuilding() {
        return building;
    }

    public int getLevel() {
        return level;
    }
}
