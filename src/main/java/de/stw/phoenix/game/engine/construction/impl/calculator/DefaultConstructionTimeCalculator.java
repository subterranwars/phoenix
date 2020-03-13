package de.stw.phoenix.game.engine.construction.impl.calculator;

import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.engine.energy.ConstructionTimeModifier;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

@Service
public class DefaultConstructionTimeCalculator implements ConstructionTimeCalculator {

    // TODO MVR remove context
    @Override
    public TimeDuration calculateConstructionTime(BuildingLevel level, ImmutablePlayer player) {
        int hqLevel = player.getBuilding(Buildings.Headquarter).getLevel();
        int levelToBuild = level.getLevel();
        double rawBuildTime = level.getBuilding().getBuildTime().getSeconds() * Math.pow(1.8, 2 * (levelToBuild - 1) / hqLevel);

        // Apply Modifier
        final double calculatedFactor = player.findModifier(ConstructionTimeModifier.class)
                .stream()
                .map(modifier -> modifier.getFactor(level, player))
                .reduce((aDouble, aDouble2) -> aDouble * aDouble2)
                .orElse(1.0);
        double calculatedBuildTime = rawBuildTime * calculatedFactor;

        // Make values a bit nicer
        long niceBuildTime = Double.valueOf(calculatedBuildTime - (calculatedBuildTime % 10)).longValue();
        return TimeDuration.ofSeconds(niceBuildTime);
    }
}
