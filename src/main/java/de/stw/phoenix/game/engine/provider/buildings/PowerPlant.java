package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.EnergyProduction;
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

import java.util.concurrent.TimeUnit;

@Service
public class PowerPlant implements GameElementProvider {
    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        final BuildingLevel building = player.getBuilding(Buildings.Powerplant);
        context.add(new EnergyProduction() {
            @Override
            public ProductionValue getProductionValue() {
                return new ProductionValue(building.getLevel() * 100, TimeDuration.ofHours(1));
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return building.getLevel() >= 1;
            }
        });
        context.add(new ResourceProduction() {
            @Override
            public Resource getResource() {
                return Resources.Oil;
            }

            @Override
            public ProductionValue getProductionValue() {
                return new ProductionValue(-1 * 100 * building.getLevel() * 1.5, TimeDuration.ofHours(1));
            }

            @Override
            public void update(MutablePlayer player, Tick tick) {
                double consumptionPerTick = getProductionValue().convert(TimeUnit.MILLISECONDS).getProductionPerTimeUnit() * tick.getDelta();
                player.removeResources(getResource(), Math.abs(consumptionPerTick)); // Have to invoke Math.abs() as retrieve is already subtracting
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return building.getLevel() >= 1;
            }
        });
    }
}
