package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.game.clock.Clock;
import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.api.MutablePlayer;
import de.stw.phoenix.game.engine.api.MutablePlayerAccessor;
import de.stw.phoenix.game.engine.modules.resources.ResourceService;
import de.stw.phoenix.game.events.GameEventCompletionService;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

@Service
public class DefaultGameEngine implements MutablePlayerAccessor, GameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGameEngine.class);

    @Autowired
    private Clock clock;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameEventCompletionService completionService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public void loop() {
        printState();
        final Tick tick = clock.nextTick();

        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            // Produce resources
            resourceService.update(eachPlayer, tick);

            // Finish stuff
            completionService.update(eachPlayer, tick);
        }
    }

    private void printState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            for (ImmutableResourceStorage resource : eachPlayer.getResources()) {
                LOG.debug("{} {}: {}", eachPlayer.getName(), resource.getResource().getName(), resource.getAmount());
            }
            for (BuildingLevel buildingLevel : eachPlayer.getBuildings()) {
                LOG.debug("{} {}: {}", eachPlayer.getName(), buildingLevel.getBuilding().getLabel(), buildingLevel.getLevel());
            }
        }
    }

    @Override
    public void requestAccess(String playerName, Consumer<MutablePlayer> consumer) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(playerName);
        final ImmutablePlayer immutablePlayer = playerService.find(playerName).get();
        synchronized (immutablePlayer) {
            final MutablePlayerImpl mutablePlayer = new MutablePlayerImpl(immutablePlayer);
            consumer.accept(mutablePlayer);
            playerService.update(mutablePlayer.asImmutable());
        }
    }
}