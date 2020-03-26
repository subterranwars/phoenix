package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.engine.energy.persistence.ConstructionTimeModifierEntity;

import java.util.function.Supplier;

public interface Modifiers {
    Supplier<ConstructionTimeModifier> CRITICAL_ENERGY_LEVEL = () -> new ConstructionTimeModifierEntity("critical-energy-level", 3.0, "Critical Energy Level");
}
