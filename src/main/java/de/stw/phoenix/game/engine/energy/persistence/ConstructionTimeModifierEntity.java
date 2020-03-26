package de.stw.phoenix.game.engine.energy.persistence;

import de.stw.phoenix.game.engine.energy.ConstructionTimeModifier;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("construction_time_modifier")
public class ConstructionTimeModifierEntity extends PlayerModifierEntity implements ConstructionTimeModifier {

    @Column(name="factor")
    private double factor;

    private ConstructionTimeModifierEntity() {

    }

    public ConstructionTimeModifierEntity(String name, double factor, String description) {
        super(name, description);
        this.factor = factor;
    }

    @Override
    public double getFactor(BuildingLevel level, Player player) {
        return factor;
    }
}
