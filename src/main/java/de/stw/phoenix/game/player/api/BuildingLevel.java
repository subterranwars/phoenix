package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.data.buildings.Building;

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

    public BuildingLevel next() {
        return new BuildingLevel(building, level + 1);
    }
}