package de.stw.phoenix.game.engine.modules;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceModule implements GameModule {

    private final ResourceService resourceService;

    @Autowired
    public ResourceModule(ResourceService resourceService) {
        this.resourceService = Objects.requireNonNull(resourceService);
    }

    @Override
    public List<PlayerUpdate> getPlayerUpdates(ImmutablePlayer player, Tick currentTick) {
        // Base production provided by HQ
        final List<PlayerUpdate> baseProductions = Resources.BASICS.stream()
                .map(resource -> (PlayerUpdate) (mutablePlayer, tick) -> {
                    final BuildingLevel hq = player.getBuilding(Buildings.Headquarter);
                    final double productionPerHour = Resources.HQ_PRODUCTION_PER_HOUR * hq.getLevel();
                    double amountToProduceInTick = productionPerHour / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
                    mutablePlayer.addResources(resource, amountToProduceInTick);
                })
                .collect(Collectors.toList());
        final List<PlayerUpdate> siteProductions = player.getResourceSites()
                .stream()
                .filter(site -> site.getDroneCount() > 0)
                .map(site -> (PlayerUpdate) (mutablePlayer, tick1) -> {
                    final double siteProductionPerHour = site.getDroneCount() * Resources.SITE_PRODUCTION_PER_HOUR;
                    double amountToProduceInTick = siteProductionPerHour / TimeConstants.MILLISECONDS_PER_HOUR * tick1.getDelta();
                    double availableAmount = Math.min(site.getStorage().getAmount(), amountToProduceInTick);
                    mutablePlayer.addResources(site.getStorage().getResource(), availableAmount);
                    mutablePlayer.getResourceSite(site.getId()).get().getStorage().retrieve(availableAmount);
                }).collect(Collectors.toList());
        final List<PlayerUpdate> allProductions = Lists.newArrayList();
        allProductions.addAll(baseProductions);
        allProductions.addAll(siteProductions);
        return allProductions;
    }
}
