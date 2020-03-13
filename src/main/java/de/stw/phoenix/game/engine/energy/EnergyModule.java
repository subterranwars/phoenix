package de.stw.phoenix.game.engine.energy;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.api.events.EventVisitor;
import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnergyModule implements GameElementProvider {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    @Autowired
    private Clock clock;

    @Autowired
    private ConstructionTimeCalculator constructionTimeCalculator;

    @Autowired
    private PlayerService playerService;

    @Subscribe
    public void onEnergyUpdateModifier(EnergyEvent energyEvent) {
        playerAccessor.modify(energyEvent.getPlayer(), mutablePlayer -> {
            if (energyEvent.getEnergyLevel() == EnergyLevel.Critical) {
                mutablePlayer.addModifier(Modifiers.CRITICAL_ENERGY_LEVEL);
            } else {
                mutablePlayer.removeModifier(Modifiers.CRITICAL_ENERGY_LEVEL);
            }
        });
    }

    @Subscribe
    public void onEnergyUpdateEvents(EnergyEvent energyEvent) {
        playerAccessor.modify(energyEvent.getPlayer(), mutablePlayer -> {
            final EventVisitor<GameEvent> visitor = new EventVisitor<GameEvent>() {

                @Override
                public GameEvent visit(ConstructionEvent constructionEvent) {
                    final ConstructionInfo constructionInfo = constructionEvent.getConstructionInfo();
                    final Building building = Buildings.findByRef(constructionEvent.getConstructionInfo().getBuilding());
                    final BuildingLevel buildingLevel = new BuildingLevel(building, constructionInfo.getLevelToBuild());
                    final ImmutablePlayer player = playerService.get(constructionEvent.getPlayerRef().getId());
                    final TimeDuration newDuration = constructionTimeCalculator.calculateConstructionTime(buildingLevel, player);
                    final ConstructionInfo newConstructionInfo = new ConstructionInfo(buildingLevel, constructionInfo.getCosts(), newDuration);
                    return new ConstructionEvent(player, newConstructionInfo, clock.getMoment(newDuration));
                }

                @Override
                public GameEvent visit(ResourceSearchEvent resourceSearchEvent) {
                    return resourceSearchEvent;
                }
            };
            final List<GameEvent> newEvents = mutablePlayer.getEvents()
                    .stream()
                    .map(e -> e.accept(visitor))
                    .collect(Collectors.toList());
            mutablePlayer.removeEvents(mutablePlayer.getEvents());
            mutablePlayer.addEvents(newEvents);
        });
    }

    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        context.add(new PlayerUpdate() {
            @Override
            public int getPhase() {
                return Phases.PostEnergyProduction;
            }

            @Override
            public void update(MutablePlayer player, Tick tick) {
                if (resourceService.getEnergyOverview(player).getProduction().getProductionPerTimeUnit() < 0) {
                    eventBus.post(new EnergyEvent(player, EnergyLevel.Critical));
                } else {
                    eventBus.post(new EnergyEvent(player, EnergyLevel.Normal));
                }
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return true;
            }
        });
    }
}
