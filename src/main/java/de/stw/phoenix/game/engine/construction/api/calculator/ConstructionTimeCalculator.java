package de.stw.phoenix.game.engine.construction.api.calculator;

import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.TimeDuration;

public interface ConstructionTimeCalculator {
    TimeDuration calculateConstructionTime(BuildingLevel level, ImmutablePlayer player);
}
