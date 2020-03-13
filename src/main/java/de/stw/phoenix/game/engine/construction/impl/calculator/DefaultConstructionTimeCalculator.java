package de.stw.phoenix.game.engine.construction.impl.calculator;

import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

@Service
public class DefaultConstructionTimeCalculator implements ConstructionTimeCalculator {

    @Override
    public TimeDuration calculateConstructionTime(BuildingLevel level, Context context, ImmutablePlayer player) {
        int hqLevel = player.getBuilding(Buildings.Headquarter).getLevel();
        int levelToBuild = level.getLevel();
        double rawBuildTime = level.getBuilding().getBuildTime().getSeconds() * Math.pow(1.8, 2 * (levelToBuild - 1) / hqLevel);

        // Make values a bit nicer
        long niceBuildTime = Double.valueOf(rawBuildTime - (rawBuildTime % 10)).longValue();
        return TimeDuration.ofSeconds(niceBuildTime);
    }
}
