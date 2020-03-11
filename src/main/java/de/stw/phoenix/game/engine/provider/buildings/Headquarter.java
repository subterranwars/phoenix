package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.EnergyProduction;
import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Headquarter implements GameElementProvider {

    @Override
    public void registerElements(final MutableContext context, final ImmutablePlayer player) {
        final List<GameElement> resourceProductions = Resources.BASICS.stream().map(resource -> new ResourceProduction() {

            @Override
            public Resource getResource() {
                return resource;
            }

            @Override
            public void update(MutablePlayer player, Tick tick) {
                final double productionPerTickUnit = getProductionValue().convert(TimeUnit.MILLISECONDS).getProductionPerTimeUnit();
                final double amountToProduceInTick = productionPerTickUnit * tick.getDelta();
                player.addResources(resource, amountToProduceInTick);
            }

            @Override
            public ProductionValue getProductionValue() {
                final BuildingLevel hqBuilding = player.getBuilding(Buildings.Headquarter);
                final double productionPerHour = hqBuilding.getLevel() * Resources.HQ_PRODUCTION_PER_HOUR;
                return new ProductionValue(productionPerHour, TimeDuration.ofHours(1));
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return player.getBuilding(Buildings.Headquarter).getLevel() >= 1;
            }
        }).collect(Collectors.toList());

        context.add(new EnergyProduction() {
            @Override
            public ProductionValue getProductionValue() {
                return new ProductionValue(100, TimeDuration.ofHours(1));
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return player.getBuilding(Buildings.Headquarter).getLevel() >= 1;
            }
        });

        context.addAll(resourceProductions);


//        gameElements.add(new ConstructionTimeModifier() {
//            @Override
//            public ConstructionInfo modify(ImmutablePlayer player, ConstructionInfo input) {
//                final Building building = Buildings.findByRef(input.getBuilding());
//                final BuildingLevel hq = player.getBuilding(Buildings.Headquarter);
////                Preconditions.checkArgument(level > 0);
////                Preconditions.checkArgument(hqLevel > 0);
//                double rawBuildTime = building.getBuildTime().getSeconds() * Math.pow(1.8, 2 * (input.getLevelToBuild() - 1) / hq.getLevel());
//                return Double.valueOf(rawBuildTime - (rawBuildTime % 10)).longValue();
//                // Make values a bit nicer
////                return new ConstructionInfo(building, input.getLevelToBuild(), hq.getLevel());
//
//                return null;
//            }
//
//            @Override
//            public boolean isActive(ImmutablePlayer player) {
//                return true;
//            }
//        });
    }
}