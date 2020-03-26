package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.EnergyProduction;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

@Service
public class Solarpanel implements GameElementProvider {
    @Override
    public void registerElements(MutableContext context, Player player) {
        context.add(new EnergyProduction() {
            @Override
            public ProductionValue getProductionValue() {
                return new ProductionValue(player.getBuilding(Buildings.Solarpanels).getLevel() * 25, TimeDuration.ofHours(1));
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                return player.getBuilding(Buildings.Solarpanels).getLevel() >= 1;
            }
        });
    }
}
