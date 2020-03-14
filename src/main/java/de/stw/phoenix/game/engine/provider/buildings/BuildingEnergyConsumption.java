package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.EnergyProduction;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

@Service
public class BuildingEnergyConsumption implements GameElementProvider {
    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        player.getBuildings().stream()
                .filter(b -> b.getBuilding().getEnergyConsumption() > 0)
                .forEach(bl -> {
                    context.add(new EnergyProduction() {
                        @Override
                        public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                            return true;
                        }

                        @Override
                        public ProductionValue getProductionValue() {
                            return new ProductionValue( - 1 * bl.getBuilding().getEnergyConsumption() * bl.getLevel(), TimeDuration.ofHours(1));
                        }
                    });
                });
    }
}
