package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.buildings.BuildingRef;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Map;
import java.util.Objects;

public class ConstructionInfo {
    // What to build
    private final BuildingRef building;

    // which level to build
    private final int levelToBuild;

    // Build time to build the level
    private final TimeDuration buildTime;

    //Costs for the level to build
    private final Map<Resource, Integer> costs;

    public ConstructionInfo(BuildingLevel buildingLevel, Map<Resource, Integer> costs, TimeDuration constructionTime) {
        this(Objects.requireNonNull(buildingLevel).getBuilding(), buildingLevel.getLevel(), costs, constructionTime);
    }

    public ConstructionInfo(BuildingRef building, int levelToBuild, Map<Resource, Integer> costs, TimeDuration constructionTime) {
        this.building = Objects.requireNonNull(building);
        this.levelToBuild = levelToBuild;
        this.costs = Objects.requireNonNull(costs);
        this.buildTime = Objects.requireNonNull(constructionTime);
    }

    public BuildingRef getBuilding() {
        return building;
    }

    public int getLevelToBuild() {
        return levelToBuild;
    }

    public TimeDuration getBuildTime() {
        return buildTime;
    }

    public Map<Resource, Integer> getCosts() {
        return costs;
    }

}
