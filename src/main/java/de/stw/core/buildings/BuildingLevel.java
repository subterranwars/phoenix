package de.stw.core.buildings;

import java.util.Objects;

public class BuildingLevel {
    private final Building building;
    private final int level;

    public BuildingLevel(final Building building, int level) {
        this.building = Objects.requireNonNull(building);
        this.level = level;
    }

    public Building getBuilding() {
        return building;
    }

    public int getLevel() {
        return level;
    }
}
