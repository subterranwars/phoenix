package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.engine.energy.persistence.ConstructionTimeModifierEntity;

public interface Modifiers {
    ConstructionTimeModifier CRITICAL_ENERGY_LEVEL = new ConstructionTimeModifierEntity("critical-energy-level", 3.0, "Critical Energy Level");
}
