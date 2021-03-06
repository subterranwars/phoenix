package de.stw.phoenix.game.engine.construction.impl;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.construction.api.ConstructionService;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionCostCalculator;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.ClockService;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultConstructionService implements ConstructionService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultConstructionService.class);

    @Autowired
    private ClockService clockService;

    @Autowired
    private ConstructionTimeCalculator constructionTimeCalculator;

    @Autowired
    private ConstructionCostCalculator constructionCostCalculator;

    @Autowired
    private EventBus eventBus;

    @Override
    @Transactional
    public List<ConstructionInfo> listConstructions(final Player player) {
        Objects.requireNonNull(player);
        return Buildings.ALL.stream()
                .filter(building -> building.getRequirement().fulfills(player))
                .map(player::getBuilding)
                .map(BuildingLevel::next)
                .map(bl -> {
                    final Map<Resource, Double> costs = constructionCostCalculator.calculateConstructionCosts(bl, player);
                    final TimeDuration constructionTime = constructionTimeCalculator.calculateConstructionTime(bl, player);
                    return new ConstructionInfo(bl, costs, constructionTime);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void build(final Player player, final Building building) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(building);
        if (!building.getRequirement().fulfills(player)) {
            // TODO MVR throw exception => does not fulfil requirements
            LOG.warn("Player {} does not fulfill requirements to build {}", player.getName(), building.getLabel());
            return;
        }
        if (!player.findSingleEvent(ConstructionEvent.class).isPresent()) {
            final BuildingLevel nextLevel = player.getBuilding(building).next();
            final Map<Resource, Double> costs = constructionCostCalculator.calculateConstructionCosts(nextLevel, player);
            final TimeDuration constructionTime = constructionTimeCalculator.calculateConstructionTime(nextLevel, player);
            if (player.canAfford(costs)) {
                // Enqueue
                final ConstructionEvent constructionEvent = new ConstructionEvent(
                        player,
                        nextLevel.getBuilding(),
                        nextLevel.getLevel(),
                        0,
                        constructionTime,
                        clockService.getCurrentTick().toMoment());
                player.addEvent(constructionEvent);

                // Subtract resources
                player.removeResources(costs);
                eventBus.post(player);
            } else {
                // TODO MVR throw exception? Cannot afford?
                LOG.warn("Player {} cannot afford to build {}", player.getName(), building.getLabel());
            }
        } else {
            // TODO MVR error handling => not allowed while already constructing
            LOG.warn("Player {} cannot build, already constructing", player.getName());
        }
    }
}
