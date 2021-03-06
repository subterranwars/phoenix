package de.stw.phoenix.game.engine.construction.impl.calculator;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionCostCalculator;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultConstructionCostCalculator implements ConstructionCostCalculator {

    @Override
    public Map<Resource, Double> calculateConstructionCosts(BuildingLevel level, Player player) {
        Objects.requireNonNull(level);
        Preconditions.checkArgument(level.getLevel() > 0);
        final Map<Resource, Double> baseBuildingCosts = level.getBuilding().getCosts().entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().doubleValue()));
        if (level.getLevel() == 1) {
            return baseBuildingCosts;
        }
        int actualLevel = level.getLevel() - 1;
        final Map<Resource, Double> calculatedCosts = baseBuildingCosts.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> {
            double baseCost = e.getValue();
            double costs = baseCost * Math.pow(2, Math.pow(actualLevel, 0.5));
            // Make values a bit nicer
            return Double.valueOf(costs - (costs % 10));
        }));
        return calculatedCosts;
    }
}
