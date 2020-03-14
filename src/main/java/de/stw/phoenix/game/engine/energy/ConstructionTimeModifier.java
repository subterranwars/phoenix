package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

public interface ConstructionTimeModifier extends PlayerModifier {
    double getFactor(BuildingLevel level, ImmutablePlayer player);
}
