package de.stw.phoenix.game.engine.resources.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.engine.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultResourceService implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceService.class);

    @Autowired
    private Clock clock;

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    @Override
    public List<ResourceOverview> getResourceProduction(final ImmutablePlayer player) {
        // Base production provided by HQ
        final Map<Resource, ResourceOverview> resourceOverviewMap = Resources.BASICS.stream()
                .map(resource -> player.getStorage(resource))
                .map(storage -> new ResourceOverview(storage, Resources.Productions.create(player, storage.getResource())))
                .collect(Collectors.toMap(resourceOverview -> resourceOverview.getResource(), Function.identity()));

        // Production provided by resource sites
        player.getResourceSites().forEach(site -> {
            // TODO MVR ensure that resource storage actually exists
            final ProductionValue siteProductionValue = Resources.Productions.create(site);
            final ImmutableResourceStorage siteStorage = site.getStorage();
            if (resourceOverviewMap.containsKey(siteStorage.getResource())) {
                ResourceOverview oldOverview = resourceOverviewMap.get(siteStorage.getResource());
                final ProductionValue oldProduction = oldOverview.getResourceProduction();
                double newProductionPerHour = oldProduction.getProductionPerTimeUnit() + siteProductionValue.getProductionPerTimeUnit();
                resourceOverviewMap.put(siteStorage.getResource(), new ResourceOverview(oldOverview.getStorage(), new ProductionValue(newProductionPerHour, TimeUnit.HOURS)));
            } else {
                resourceOverviewMap.put(siteStorage.getResource(), new ResourceOverview(player.getStorage(siteStorage.getResource()), siteProductionValue));
            }
        });
        return Collections.unmodifiableList(Lists.newArrayList(resourceOverviewMap.values()));
    }

    // TODO MVR implement limit of things we can search
    @Override
    public void search(ResourceSearchRequest resourceSearchRequest) {
        // TODO MVR ensure user actually has resource building before searching is supported
        final PlayerRef playerRef = resourceSearchRequest.getPlayerRef();
        final Moment userCompletionMoment = clock.getMoment(resourceSearchRequest.getDuration());
        final Moment internalMoment = calculateFinishMoment(resourceSearchRequest);
        final ResourceSearchEvent resourceSearchEvent = new ResourceSearchEvent(new ResourceSearchInfo(resourceSearchRequest.getResource(), resourceSearchRequest.getDuration(), internalMoment), userCompletionMoment);
        playerAccessor.modify(playerRef, mutablePlayer -> {
            mutablePlayer.addEvent(resourceSearchEvent);
        });
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} searching for {} for {} hours which is successful {} and completes at moment {}",
                    playerRef.getName(),
                    resourceSearchEvent.getInfo().getResource().getName(),
                    resourceSearchEvent.getInfo().getDuration().getHours(),
                    resourceSearchEvent.getInfo().isSuccess(),
                    resourceSearchEvent.getCompletionMoment().asSeconds());
        }
    }

    @Override
    public void deleteResourceSite(ImmutablePlayer player, long resourceSiteId) {
        // TODO MVR add exception if resourceSiteId is not available
        playerAccessor.modify(player, mutablePlayer -> mutablePlayer.getResourceSite(resourceSiteId)
                .ifPresent(mutableResourceSite -> mutablePlayer.removeResourceSite(mutableResourceSite)));
    }

    @Override
    public void updateDroneCount(ImmutablePlayer player, long resourceSiteId, int droneCount) {
        // TODO MVR add exception if resourceSiteId is not available
        // TODO MVR ensure droneCount is >= 0
        // TODO MVR ensure max droneCount is not overdoing it
        playerAccessor.modify(player, mutablePlayer -> {
            mutablePlayer.getResourceSite(resourceSiteId).ifPresent(site -> {
                site.setDroneCount(droneCount);
            });
        });
    }

    // Returns the moment when the resource was detected, otherwise null
    private Moment calculateFinishMoment(ResourceSearchRequest resourceSearchRequest) {
        // TODO MVR use getHours() instead of getSeconds()
        for (int i = 0; i < resourceSearchRequest.getDuration().getSeconds(); i++) {
            if (Math.random() < resourceSearchRequest.getResource().getOccurrence()) {
                final Moment internalMoment = clock.getMoment(TimeDuration.ofSeconds(i + 1 * 30)); // TODO MVR use actual hours instead of seconds
                return internalMoment;
            }
        }
        return null;
    }
}
