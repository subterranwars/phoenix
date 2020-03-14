package de.stw.phoenix.game.engine.construction.api.calculator;

import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.Map;

public interface ConstructionCostCalculator {
    Map<Resource, Integer> calculateConstructionCosts(BuildingLevel level, ImmutablePlayer player);
}
