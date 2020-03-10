package de.stw.phoenix.game.engine.modules;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
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
        final List<PlayerUpdate> allProductions = Lists.newArrayList();
        allProductions.addAll(baseProductions);
        allProductions.addAll(siteProductions);
        return allProductions;
    }
}
