package de.stw.phoenix.game.engine.requirements;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.player.impl.Player;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("building")
public class BuildingRequirement extends RequirementEntity {

    @OneToOne
    @JoinColumn(name="building_id")
    private Building building;

    @Column(name="level")
    private int level;

    private BuildingRequirement() {

    }

    protected BuildingRequirement(Building building, int level) {
        this.building = building;
        this.level = level;
    }

    @Override
    public boolean fulfills(Player player) {
        return player.getBuilding(building).getLevel() >= level;
    }
}
