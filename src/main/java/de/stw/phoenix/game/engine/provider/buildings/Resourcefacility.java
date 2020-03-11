package de.stw.phoenix.game.engine.provider.buildings;

import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.ResourceProduction;
import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Resourcefacility implements GameElementProvider {
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
    }
}
