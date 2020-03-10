package de.stw.phoenix.game.engine.modules;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceModule implements GameModule {

    @Override
    public List<PlayerUpdate> getPlayerUpdates(ImmutablePlayer player, Tick currentTick) {
        // Base production provided by HQ
        final List<PlayerUpdate> baseProductions = Resources.BASICS.stream()
                .map(resource -> (PlayerUpdate) (mutablePlayer, tick) -> {
                    final double productionPerHour = Resources.Productions.create(player, resource).getProductionPerTimeUnit();
                    double amountToProduceInTick = productionPerHour / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
                    mutablePlayer.addResources(resource, amountToProduceInTick);
                })
                .collect(Collectors.toList());
        final List<PlayerUpdate> siteProductions = player.getResourceSites()
                .stream()
                .filter(site -> site.getDroneCount() > 0)
                .map(site -> (PlayerUpdate) (mutablePlayer, tick) -> {
                    final double siteProductionPerHour = Resources.Productions.create(site).getProductionPerTimeUnit();
                    double amountToProduceInTick = siteProductionPerHour / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
                    double availableAmount = Math.min(site.getStorage().getAmount(), amountToProduceInTick);
                    mutablePlayer.addResources(site.getStorage().getResource(), availableAmount);
                    mutablePlayer.getResourceSite(site.getId()).get().getStorage().retrieve(availableAmount);
                }).collect(Collectors.toList());

        final List<PlayerUpdate> allUpdates = Lists.newArrayList();
        allUpdates.addAll(baseProductions);
        allUpdates.addAll(siteProductions);


        // TODO MVR this is ugly
        allUpdates.set(0, new PlayerUpdate() {
            @Override
            public void update(MutablePlayer player, Tick tick) {

            }

            @Override
            public void preUpdate(MutablePlayer player, Tick tick) {
                final BuildingLevel building = player.getBuilding(Buildings.Resourcedepot);
                final long maxStorage = Resources.MAX_STORAGE_CAPACITY + (building.getLevel() * Resources.STORAGE_CAPACITY_GAIN);
                player.updateResourceStorage(maxStorage);
            }

            @Override
            public void postUpdate(MutablePlayer player, Tick tick) {
                preUpdate(player, tick);
            }
        });

        return allUpdates;
    }
}
