package de.stw.phoenix.game.engine.impl;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.api.ResourceStorage;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    private List<GameElementProvider> elementProviderList;

    @Autowired
    private EventBus eventBus;

    @Override
    @Transactional
    public void loop() {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "Session must be active");
        logState();
        final Tick tick = clock.nextTick();
        for (Player player : playerService.getPlayers()) {
            final List<PlayerUpdate> playerUpdateList = getContext(player)
                .findElements(PlayerUpdate.class).stream()
                .sorted(Comparator.comparing(PlayerUpdate::getPhase))
                .collect(Collectors.toList());
            for (PlayerUpdate playerUpdate : playerUpdateList) {
                if (playerUpdate.isActive(player, tick)) {
                    playerUpdate.update(player, tick);
                }
            }
            // Notify listeners about player changes
            eventBus.post(player);
        }
    }

    @Override
    @Transactional
    public Context getContext(Player player) {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "Session must be active");
        final MutableContext context = new DefaultMutableContext(clock.getCurrentTick());
        elementProviderList.stream().forEach(provider -> provider.registerElements(context, player));
        return context.asImmutable();
    }

    private void logState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (Player eachPlayer : playerService.getPlayers()) {
            for (ResourceStorage resource : eachPlayer.getResources()) {
                LOG.trace("{} {}: {}", eachPlayer.getName(), resource.getResource().getName(), resource.getAmount());
            }
            for (BuildingLevel buildingLevel : eachPlayer.getBuildings()) {
                LOG.trace("{} {}: {}", eachPlayer.getName(), buildingLevel.getBuilding().getLabel(), buildingLevel.getLevel());
            }
        }
    }
}