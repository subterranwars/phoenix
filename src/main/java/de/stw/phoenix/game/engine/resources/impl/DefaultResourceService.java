package de.stw.phoenix.game.engine.resources.impl;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.api.EnergyProduction;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.energy.EnergyOverview;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.ResourceOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.ResourceStorage;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.ClockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultResourceService implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceService.class);

    @Autowired
    private ClockService clockService;

    @Autowired
    private GameEngine gameEngine;

    @Autowired
    private EventBus eventBus;

    @Override
    @Transactional
    public List<ResourceOverview> getResourceOverview(Player player) {
        final Context context = gameEngine.getContext(player);
        final Collection<ResourceOverview> overviews = context.findElements(ResourceProduction.class)
                .stream()
                .filter(p -> p.isActive(player, context.getCurrentTick()))
                .map(production -> {
                    final ResourceStorage storage = player.getStorage(production.getResource());
                    return new ResourceOverview(storage, production.getProductionValue());
                }).collect(Collectors.toMap(overview -> overview.getResource(), Function.identity(), (resourceOverview, resourceOverview2) -> {
                    double productionValue = resourceOverview.getResourceProduction().getProductionPerTimeUnit() + resourceOverview2.getResourceProduction().getProductionPerTimeUnit();
                    return new ResourceOverview(resourceOverview.getStorage(), new ProductionValue(productionValue, resourceOverview.getResourceProduction().getTimeUnit()));
                })).values();
        return new ArrayList<>(overviews);
    }

    @Override
    @Transactional
    public EnergyOverview getEnergyOverview(Player player) {
        final Context context = gameEngine.getContext(player);
        final double energyLevel = context.findElements(EnergyProduction.class)
                .stream()
                .filter(p -> p.isActive(player, context.getCurrentTick()))
                .mapToDouble(p -> p.getProductionValue().convert(TimeUnit.HOURS).getProductionPerTimeUnit())
                .sum();
        return new EnergyOverview(energyLevel);
    }

    // TODO MVR implement limit of things we can search
    @Override
    @Transactional
    public void search(ResourceSearchRequest resourceSearchRequest) {
        // TODO MVR ensure user actually has resource building before searching is supported
        final Player player = resourceSearchRequest.getPlayer();
        final ResourceSearchEvent resourceSearchEvent = new ResourceSearchEvent(player, resourceSearchRequest.getResource(), clockService.getCurrentTick().toMoment());
        player.addEvent(resourceSearchEvent);
        eventBus.post(player);
        LOG.debug("{} searching for {}", player.getId(), resourceSearchEvent.getResource().getName());
    }

    @Override
    @Transactional
    public void deleteResourceSite(Player player, long resourceSiteId) {
        // TODO MVR add exception if resourceSiteId is not available
        player.getResourceSite(resourceSiteId).ifPresent(resourceSite -> player.removeResourceSite(resourceSite));
        eventBus.post(player);
    }

    @Override
    @Transactional
    public void updateDroneCount(Player player, long resourceSiteId, int droneCount) {
        // TODO MVR add exception if resourceSiteId is not available
        // TODO MVR ensure droneCount is >= 0
        // TODO MVR ensure max droneCount is not overdoing it
        player.getResourceSite(resourceSiteId).ifPresent(site -> site.setDroneCount(droneCount));
        eventBus.post(player);
    }
}
