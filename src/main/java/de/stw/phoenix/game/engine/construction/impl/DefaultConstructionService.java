package de.stw.phoenix.game.engine.construction.impl;

import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.construction.api.ConstructionService;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionCostCalculator;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultConstructionService implements ConstructionService {

    @Autowired
    private Clock clock;

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    @Autowired
    private ConstructionTimeCalculator constructionTimeCalculator;

    @Autowired
    private ConstructionCostCalculator constructionCostCalculator;

    @Autowired
    private GameEngine gameEngine;

    @Override
    public List<ConstructionInfo> listConstructions(final ImmutablePlayer player) {
        Objects.requireNonNull(player);
        return Buildings.ALL.stream()
                .map(player::getBuilding)
                .map(BuildingLevel::next)
                .map(bl -> {
                    final Map<Resource, Integer> costs = constructionCostCalculator.calculateConstructionCosts(bl, player);
                    final TimeDuration constructionTime = constructionTimeCalculator.calculateConstructionTime(bl, player);
                    return new ConstructionInfo(bl, costs, constructionTime);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void build(final ImmutablePlayer player, final Building building) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(building);
        if (!player.findSingleEvent(ConstructionEvent.class).isPresent()) {
            final BuildingLevel nextLevel = player.getBuilding(building).next();
            final Map<Resource, Integer> costs = constructionCostCalculator.calculateConstructionCosts(nextLevel, player);
            final TimeDuration constructionTime = constructionTimeCalculator.calculateConstructionTime(nextLevel, player);
            final ConstructionInfo constructionInfo =  new ConstructionInfo(nextLevel, costs, constructionTime);
            if (player.canAfford(constructionInfo.getCosts())) {
                playerAccessor.modify(player, mutablePlayer -> {
                    // Enqueue
                    final ConstructionEvent constructionEvent = new ConstructionEvent(
                            mutablePlayer,
                            constructionInfo,
                            0,
                            constructionInfo.getBuildTime(),
                            clock.getCurrentTick().toMoment());
                    mutablePlayer.addEvent(constructionEvent);

                    // Subtract resources
                    mutablePlayer.removeResources(constructionInfo.getCosts());
                });
            } else {
                // TODO MVR throw exception? Cannot afford?
            }
        } else {
            // TODO MVR error handling => not allowed while already constructing
        }
    }
}
