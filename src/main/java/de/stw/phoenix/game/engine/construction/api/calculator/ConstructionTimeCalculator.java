package de.stw.phoenix.game.engine.construction.api.calculator;

import de.stw.phoenix.game.engine.buildings.BuildingRef;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.TimeDuration;

public interface ConstructionTimeCalculator {
    TimeDuration calculateConstructionTime(BuildingLevel level, ImmutablePlayer player);

    default TimeDuration calculateConstructionTime(BuildingRef building, int level, ImmutablePlayer player) {
        return calculateConstructionTime(new BuildingLevel(Buildings.findByRef(building), level), player);
    }
}
