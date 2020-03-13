package de.stw.phoenix.game.engine.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultGameEngine implements GameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGameEngine.class);

    @Autowired
    private Clock clock;

    @Autowired
    private PlayerService playerService;

    @Autowired
    final List<GameElementProvider> elementProviderList = Lists.newArrayList();

    @Override
    public void loop() {
        logState();
        final Tick tick = clock.nextTick();
        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            playerService.modify(eachPlayer, mutablePlayer -> {
                final List<PlayerUpdate> playerUpdateList = getContext(eachPlayer)
                    .findElements(PlayerUpdate.class).stream()
                    .filter(pu -> pu.isActive(mutablePlayer.asImmutable(), tick))
                    .sorted(Comparator.comparing(PlayerUpdate::getPhase))
                    .collect(Collectors.toList());
                // Pre Update
                for (PlayerUpdate playerUpdate : playerUpdateList) {
                    playerUpdate.preUpdate(mutablePlayer, tick);
                }
                // Update
                for (PlayerUpdate playerUpdate : playerUpdateList) {
                    playerUpdate.update(mutablePlayer, tick);
                }
                // Post Update
                for (PlayerUpdate playerUpdate : playerUpdateList) {
                    playerUpdate.postUpdate(mutablePlayer, tick);
                }
            });
        }
    }

    @Override
    public Context getContext(ImmutablePlayer player) {
        final MutableContext context = new DefaultMutableContext(clock.getCurrentTick());
        playerService.modify(player, mutablePlayer -> elementProviderList.stream().forEach(provider -> provider.registerElements(context, mutablePlayer)));
        return context.asImmutable();
    }

    private void logState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            for (ImmutableResourceStorage resource : eachPlayer.getResources()) {
                LOG.trace("{} {}: {}", eachPlayer.getName(), resource.getResource().getName(), resource.getAmount());
            }
            for (BuildingLevel buildingLevel : eachPlayer.getBuildings()) {
                LOG.trace("{} {}: {}", eachPlayer.getName(), buildingLevel.getBuilding().getLabel(), buildingLevel.getLevel());
            }
        }
    }
}