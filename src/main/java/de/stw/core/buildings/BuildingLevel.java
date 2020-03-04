package de.stw.core.buildings;

import de.stw.core.resources.Resource;

import java.util.Map;
import java.util.Objects;

public class BuildingLevel {
    private final Building building;
    private final int level;
    private final Map<Resource, Integer> costs;

    public BuildingLevel(final Building building, int level) {
        this.building = Objects.requireNonNull(building);
        this.level = level;
        this.costs = building.calculateCosts(level + 1);
    }

    public Building getBuilding() {
        return building;
    }

    public int getLevel() {
        return level;
    }

    // Gets the costs for the NEXT level
    public Map<Resource, Integer> getCosts() {
        return costs;
    }

    public BuildingLevel next() {
        return new BuildingLevel(building, level + 1);
    }
}
