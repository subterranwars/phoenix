package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.buildings.Building;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

// TODO MVR identical to ResearchLevel
@Entity
@Table(name="player_buildings")
public class BuildingLevel {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="building_id", nullable = false)
    private Building building;

    @Column(name="level")
    private int level;

    private BuildingLevel() {
    }

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
