package de.stw.phoenix.game.engine.energy;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.api.EnergyProduction;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class EnergyModule implements GameElementProvider {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private PlayerService playerService;

    // TODO MVR when the modifier is added we should update the build times as well as that is only
    //  happening the next tick (could be okay though)
    @Subscribe
    public void onEnergyUpdateModifier(EnergyEvent energyEvent) {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "No active session");
        final Player player = energyEvent.getPlayer();
        if (energyEvent.getEnergyLevel() == EnergyLevel.Critical) {
            player.addModifier(Modifiers.CRITICAL_ENERGY_LEVEL.get());
        } else {
            player.removeModifier(Modifiers.CRITICAL_ENERGY_LEVEL.get());
        }
    }

    @Override
    public void registerElements(MutableContext context, Player player) {
        player.getBuildings().stream()
                .filter(b -> b.getBuilding().getEnergyConsumption() > 0)
                .forEach(bl -> {
                    context.add(new EnergyProduction() {
                        @Override
                        public boolean isActive(Player player, Tick currentTick) {
                            return true;
                        }

                        @Override
                        public ProductionValue getProductionValue() {
                            return new ProductionValue( - 1 * bl.getBuilding().getEnergyConsumption() * bl.getLevel(), TimeDuration.ofHours(1));
                        }
                    });
                });

        context.add(new PlayerUpdate() {
            @Override
            public int getPhase() {
                return Phases.PostEnergyProduction;
            }

            @Override
            public void update(Player player, Tick tick) {
                if (resourceService.getEnergyOverview(player).getProduction().getProductionPerTimeUnit() < 0) {
                    eventBus.post(new EnergyEvent(player, EnergyLevel.Critical));
                } else {
                    eventBus.post(new EnergyEvent(player, EnergyLevel.Normal));
                }
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                return true;
            }
        });
    }
}
