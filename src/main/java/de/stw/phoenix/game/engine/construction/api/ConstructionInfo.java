package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Map;
import java.util.Objects;

public class ConstructionInfo {
    // TODO MVR use BuildingRef where possible?
    // What to build
    private final Building building;

    // which level to build
    private final int levelToBuild;

    // Build time to build the level
    private final TimeDuration buildTime;

    //Costs for the level to build
    private final Map<Resource, Double> costs;

    public ConstructionInfo(BuildingLevel buildingLevel, Map<Resource, Double> costs, TimeDuration constructionTime) {
        this(Objects.requireNonNull(buildingLevel).getBuilding(), buildingLevel.getLevel(), costs, constructionTime);
    }

    public ConstructionInfo(Building building, int levelToBuild, Map<Resource, Double> costs, TimeDuration constructionTime) {
        this.building = Objects.requireNonNull(building);
        this.levelToBuild = levelToBuild;
        this.costs = Objects.requireNonNull(costs);
        this.buildTime = Objects.requireNonNull(constructionTime);
    }

    public Building getBuilding() {
        return building;
    }

    public int getLevelToBuild() {
        return levelToBuild;
    }

    public TimeDuration getBuildTime() {
        return buildTime;
    }

    public Map<Resource, Double> getCosts() {
        return costs;
    }

}
