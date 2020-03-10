package de.stw.phoenix.game.engine.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.modules.CompletionModule;
import de.stw.phoenix.game.engine.modules.ResourceModule;
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

import java.util.List;

@Service
public class DefaultGameEngine implements GameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultGameEngine.class);

    @Autowired
    private Clock clock;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CompletionModule completionService;

    @Autowired
    private ResourceModule resourceModule;

    @Override
    public void loop() {
        logState();
        final Tick tick = clock.nextTick();
        for (ImmutablePlayer eachPlayer : playerService.getPlayers()) {
            playerService.modify(eachPlayer, mutablePlayer -> {
                final List<PlayerUpdate> playerUpdateList = Lists.newArrayList();

                // Produce resources
                playerUpdateList.addAll(resourceModule.getPlayerUpdates(mutablePlayer, tick));

                // Finish stuff
                playerUpdateList.addAll(completionService.getPlayerUpdates(mutablePlayer, tick));

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