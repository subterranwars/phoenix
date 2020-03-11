package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.springframework.stereotype.Service;

@Service
public class ResourceDepot implements GameElementProvider {
    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        context.add(new PlayerUpdate() {
            @Override
            public int getPhase() {
                return Phases.ResourceProduction;
            }

            @Override
            public void preUpdate(MutablePlayer player, Tick tick) {
                BuildingLevel building = player.getBuilding(Buildings.Resourcedepot);
                final long maxStorage = Resources.MAX_STORAGE_CAPACITY + Resources.STORAGE_CAPACITY_GAIN * building.getLevel();
                player.updateResourceStorage(maxStorage);
            }

            @Override
            public void update(MutablePlayer player, Tick tick) {

            }

            @Override
            public void postUpdate(MutablePlayer player, Tick tick) {
            }

            @Override
            public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                return player.getBuilding(Buildings.Resourcedepot).getLevel() >= 1;
            }
        });
    }
}
