package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.Map;
import java.util.Objects;

@Embeddable
public class ConstructionInfo {
    // TODO MVR use BuildingRef where possible?
    // What to build
    @OneToOne
    @JoinColumn(name="building_id")
    private Building building;

    // which level to build
    @Column(name="level")
    private int levelToBuild;

    // Build time to build the level
    @Transient
    private TimeDuration buildTime;

    //Costs for the level to build
    @Transient
    private Map<Resource, Double> costs;

    private ConstructionInfo() {

    }

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
