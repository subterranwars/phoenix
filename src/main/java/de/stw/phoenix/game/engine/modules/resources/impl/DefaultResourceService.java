package de.stw.phoenix.game.engine.modules.resources.impl;

import de.stw.phoenix.game.data.resources.Resources;
import de.stw.phoenix.game.engine.modules.resources.api.ResearchSearchInfo;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceProduction;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultResourceService implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceService.class);

    @Autowired
    private Clock clock;

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    @Override
    public List<ResourceProduction> getResourceProduction(final ImmutablePlayer player) {
        return player.getResources().stream()
                .map(storage -> new ResourceProduction(player, storage, 60))
                .collect(Collectors.toList());
    }

    // TODO MVR implement limit of things we can search
    @Override
    public void search(ResourceSearchRequest resourceSearchRequest) {
        final PlayerRef playerRef = resourceSearchRequest.getPlayerRef();
        final Moment userCompletionMoment = clock.getMoment(resourceSearchRequest.getDuration());
        final Moment internalMoment = calculateFinishMoment(resourceSearchRequest);
        final ResourceSearchEvent resourceSearchEvent = new ResourceSearchEvent(playerRef, new ResearchSearchInfo(resourceSearchRequest.getResource(), resourceSearchRequest.getDuration(), internalMoment), userCompletionMoment);
        playerAccessor.modify(playerRef, mutablePlayer -> {
            mutablePlayer.addEvent(resourceSearchEvent);
        });
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} searching for {} for {} hours which is successful {} and completes at moment {}",
                    resourceSearchEvent.getPlayerRef().getName(),
                    resourceSearchEvent.getInfo().getResource().getName(),
                    resourceSearchEvent.getInfo().getDuration().getHours(),
                    resourceSearchEvent.getInfo().isSuccess(),
                    resourceSearchEvent.getCompletionMoment().asSeconds());
        }
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

    // TODO MVR remove me
    @Autowired
    private PlayerService playerService;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void initSearch() {
        final PlayerRef player = playerService.get(1);
        final ResourceSearchRequest request = new ResourceSearchRequest(player, Resources.Iron, TimeDuration.ofHours(10));
        search(request);
    }
}
