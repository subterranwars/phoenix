package de.stw.phoenix.game.engine.research.impl.calculator;

import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.research.api.calculator.ResearchTimeCalculator;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

@Service
public class DefaultResearchTimeCalculator implements ResearchTimeCalculator {

    @Override
    public TimeDuration calculateResearchTime(ResearchLevel level, ImmutablePlayer player) {
        int labLevel = player.getBuilding(Buildings.Researchlab).getLevel();
        int levelToResearch = level.getLevel();
        double rawBuildTime = level.getResearch().getResearchTime().getSeconds() * Math.pow(1.8, 2 * (levelToResearch - 1) / labLevel);
        double calculatedBuildTime = rawBuildTime;

        // Apply Modifier
        // TODO MVR maybe add another ResearchSpeedModifier
//        final double calculatedFactor = player.findModifier(ConstructionTimeModifier.class)
//                .stream()
//                .map(modifier -> modifier.getFactor(level, player))
//                .reduce((aDouble, aDouble2) -> aDouble * aDouble2)
//                .orElse(1.0);
//        double calculatedBuildTime = rawBuildTime * calculatedFactor;

        // Make values a bit nicer
        long niceBuildTime = Double.valueOf(calculatedBuildTime - (calculatedBuildTime % 10)).longValue();
        return TimeDuration.ofSeconds(niceBuildTime);
    }

}
