package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Resourcefacility implements GameElementProvider {

    final SecureRandom secureRandom = new SecureRandom();

    @Override
    public void registerElements(MutableContext context, ImmutablePlayer player) {
        final List<GameElement> siteProductions = player.getResourceSites()
            .stream()
            .filter(site -> site.getDroneCount() > 0)
            .map(site -> new ResourceProduction() {

                @Override
                public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                    return !player.getResourceSites().isEmpty();
                }

                @Override
                public Resource getResource() {
                    return site.getStorage().getResource();
                }

                @Override
                public ProductionValue getProductionValue() {
                    return new ProductionValue(site.getDroneCount() * Resources.SITE_PRODUCTION_PER_HOUR, TimeDuration.ofHours(1));
                }

                @Override
                public void update(MutablePlayer player, Tick tick) {
                    final double siteProductionPerTickUnit = getProductionValue().convert(TimeUnit.MILLISECONDS).getProductionPerTimeUnit();
                    final double amountToProduceInTick = siteProductionPerTickUnit * tick.getDelta();
                    final double availableAmount = Math.min(site.getStorage().getAmount(), amountToProduceInTick);
                    player.addResources(site.getStorage().getResource(), availableAmount);
                    player.getResourceSite(site.getId()).get().getStorage().retrieve(availableAmount);
                }
            }).collect(Collectors.toList());
        context.addAll(siteProductions);

        // For each resourceSearchEvent we add an update
        player.findEvents(ResourceSearchEvent.class)
            .forEach(event -> context.add(new PlayerUpdate() {
                @Override
                public int getPhase() {
                    return Phases.FinishEvents;
                }

                @Override
                public void update(MutablePlayer player, Tick tick) {
                    if (Math.random() < event.getResource().getOccurrence()) {
                        final long amount = (long) (Math.random() * 100000);
                        LoggerFactory.getLogger(getClass()).info("Completing resource search event. User: {}, Resource: {}, Amount: {}", player.getName(), event.getResource().getName(), amount);
                        final ResourceSite resourceSite = new ResourceSite(secureRandom.nextInt(), new ImmutableResourceStorage(event.getResource(), amount, amount), 0);
                        player.addResourceSite(resourceSite);
                        player.removeEvent(event);
                    } else {
                        player.removeEvent(event);
                        player.addEvent(new ResourceSearchEvent(player, event.getResource(), tick.toMoment()));
                    }
                }

                @Override
                public boolean isActive(ImmutablePlayer player, Tick currentTick) {
                    return event.getLastUpdate().getDiff(currentTick.toMoment()) >= TimeDuration.ofSeconds(30).getSeconds(); // TODO MVR implement once per hour
                }
            }));
    }
}
