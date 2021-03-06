package de.stw.phoenix.game.engine.provider.buildings;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ResourceSite;
import de.stw.phoenix.game.player.api.ResourceStorage;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Resourcefacility implements GameElementProvider {

    @Autowired
    private EventBus eventBus;    

    @Subscribe
    public void onConstructionCompleted(ConstructionEvent constructionEvent) {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "No active session");
        if (Buildings.findByRef(constructionEvent.getBuilding()) == Buildings.Resourcefacility) {
            final Player player = constructionEvent.getPlayer();
            final BuildingLevel building = player.getBuilding(Buildings.Resourcefacility);
            long droneIncrease = 5 + building.getLevel() - 1;
            long totalDrones = player.getTotalDroneCount() + droneIncrease;
            player.updateTotalDroneCount(totalDrones);
        }
    }

    @Override
    public void registerElements(MutableContext context, Player player) {
        final List<GameElement> siteProductions = player.getResourceSites()
            .stream()
            .filter(site -> site.getDroneCount() > 0)
            .map(site -> new ResourceProduction() {

                @Override
                public boolean isActive(Player player, Tick currentTick) {
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
                public void update(Player player, Tick tick) {
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
                public void update(Player player, Tick tick) {
                    if (Math.random() < event.getResource().getOccurrence()) {
                        final long amount = (long) (Math.random() * 100000);
                        LoggerFactory.getLogger(getClass()).info("Completing resource search event. User: {}, Resource: {}, Amount: {}", player.getName(), event.getResource().getName(), amount);
                        final ResourceSite resourceSite = new ResourceSite(new ResourceStorage(event.getResource(), amount, amount), 0);
                        eventBus.post(event);
                        player.addResourceSite(resourceSite);
                        player.removeEvent(event);
                    } else {
                        event.setLastUpdated(tick.toMoment());
                    }
                }

                @Override
                public boolean isActive(Player player, Tick currentTick) {
                    return event.getLastUpdate().getDiff(currentTick.toMoment()) >= TimeDuration.ofSeconds(30).getSeconds(); // TODO MVR implement once per hour
                }
            }));
    }
}
