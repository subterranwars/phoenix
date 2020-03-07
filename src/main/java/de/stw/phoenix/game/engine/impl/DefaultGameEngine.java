package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.game.clock.Clock;
import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.modules.resources.ResourceModule;
import de.stw.phoenix.game.events.GameEventCompletionModule;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultGameEngine implements GameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGameEngine.class);

    @Autowired
    private Clock clock;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameEventCompletionModule completionService;

    @Autowired
    private ResourceModule resourceModule;

    @Override
    public void loop() {
        logState();
        final Tick tick = clock.nextTick();
        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            playerService.modify(eachPlayer, mutablePlayer -> {
                // Produce resources
                resourceModule.update(mutablePlayer, tick);

                // Finish stuff
                completionService.update(mutablePlayer, tick);
            });
        }
    }

    private void logState() {
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
}