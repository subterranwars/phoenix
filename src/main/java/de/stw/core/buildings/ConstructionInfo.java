package de.stw.core.buildings;

import com.google.common.base.Preconditions;
import de.stw.core.resources.Resource;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConstructionInfo {
    // What to build
    private final BuildingRef building;

    // which level to build
    private final int levelToBuild;

    // Build time in seconds to build the level
    private final long buildTimeInSeconds;

    //Costs for the level to build
    private final Map<Resource, Integer> costs;

    protected ConstructionInfo(final BuildingLevel buildingLevel) {
        this(buildingLevel, new BuildingLevel(Buildings.Headquarter, 1));
    }

    public ConstructionInfo(final BuildingLevel buildingLevel, final BuildingLevel hqBuildingLevel) {
        this(buildingLevel.getBuilding(), buildingLevel.getLevel(), hqBuildingLevel.getLevel());
    }

    protected ConstructionInfo(Building building, int levelToBuild) {
        this(building, levelToBuild, 1);
    }

    public ConstructionInfo(Building building, int levelToBuild, int currentHqLevel) {
        this.building = Objects.requireNonNull(building);
        this.levelToBuild = levelToBuild;
        this.buildTimeInSeconds = calculateBuildTimeInSeconds(building, levelToBuild, currentHqLevel);
        this.costs = calculateCosts(building, levelToBuild);
    }

    public BuildingRef getBuilding() {
        return building;
    }

    public int getLevelToBuild() {
        return levelToBuild;
    }

    public long getBuildTimeInSeconds() {
        return buildTimeInSeconds;
    }

    public Map<Resource, Integer> getCosts() {
        return costs;
    }

    protected static Map<Resource, Integer> calculateCosts(Building building, int level) {
        Preconditions.checkArgument(level > 0);
        if (level == 1) {
            return building.getCosts();
        }
        int actualLevel = level - 1;
        final Map<Resource, Integer> calculatedCosts = building.getCosts().entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> {
            int baseCost = e.getValue();
            double costs = baseCost * Math.pow(2, Math.pow(actualLevel, 0.5));
            // Make values a bit nicer
            return Double.valueOf(costs - (costs % 10)).intValue();
        }));
        return calculatedCosts;
    }

    protected static long calculateBuildTimeInSeconds(Building building, int level, int hqLevel) {
        Preconditions.checkArgument(level > 0);
        Preconditions.checkArgument(hqLevel > 0);
        double rawBuildTime = building.getBuildTimeInSeconds() * Math.pow(1.8, 2 * (level - 1) / hqLevel);
        // Make values a bit nicer
        return Double.valueOf(rawBuildTime - (rawBuildTime % 10)).longValue();
    }
}
