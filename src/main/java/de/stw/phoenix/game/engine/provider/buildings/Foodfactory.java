package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class Foodfactory implements GameElementProvider {
    @Override
    public void registerElements(MutableContext context, Player player) {
        context.add(new ResourceProduction() {
            @Override
            public Resource getResource() {
                return Resources.Food;
            }

            @Override
            public ProductionValue getProductionValue() {
                final BuildingLevel factoryBuilding = player.getBuilding(Buildings.Foodfactory);
                final double productionPerHour = factoryBuilding.getLevel() * Resources.HQ_PRODUCTION_PER_HOUR;
                return new ProductionValue(productionPerHour, TimeDuration.ofHours(1));
            }

            @Override
            public void update(Player player, Tick tick) {
                final double productionPerTickUnit = getProductionValue().convert(TimeUnit.MILLISECONDS).getProductionPerTimeUnit();
                final double amountToProduceInTick = productionPerTickUnit * tick.getDelta();
                player.addResources(getResource(), amountToProduceInTick);
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                return player.getBuilding(Buildings.Foodfactory).getLevel() >= 1;
            }
        });
    }
}
