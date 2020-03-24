package de.stw.phoenix.game.engine.construction.api.calculator;

import de.stw.phoenix.game.engine.buildings.BuildingRef;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.TimeDuration;

public interface ConstructionTimeCalculator {
    TimeDuration calculateConstructionTime(BuildingLevel level, Player player);

    default TimeDuration calculateConstructionTime(BuildingRef building, int level, Player player) {
        return calculateConstructionTime(new BuildingLevel(Buildings.findByRef(building), level), player);
    }
}
