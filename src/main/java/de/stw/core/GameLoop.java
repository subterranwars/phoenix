package de.stw.core;

import com.google.common.collect.Lists;
import de.stw.core.clock.ArtificialClock;
import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;
import de.stw.rest.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static de.stw.core.resources.Resources.*;

@Service
public class GameLoop {

    private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);

    private final Clock clock;
    private final List<Player> players;
	private final List<ResourceProduction> processes;
    private GameState state;

    public GameLoop() {
        this.clock = new ArtificialClock(10, TimeUnit.SECONDS);
        this.players = Lists.newArrayList(
                new Player(1, "marskuh", createInitialStorages()),
                new Player(2, "fafner", createInitialStorages())
        );
        this.players.get(0).getResources().forEach(rp -> {
            rp.store(1000);
        });
        this.processes = players.stream()
				.flatMap(p -> p.getResources().stream())
				.map(storage -> new ResourceProduction(storage, 60))
				.collect(Collectors.toList());
        updateState();
    }

    public void loop() {
        printState();
        final Tick tick = clock.nextTick();
        for (ResourceProduction production : processes) {
            production.update(tick);
        }
        updateState();
    }

    private void printState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (Player eachPlayer : players) {
            for (ResourceStorage resource : eachPlayer.getResources()) {
                LOG.debug("{} {}: {}", eachPlayer.getName(), resource.getResource().getName(), resource.getAmount());
            }
        }
    }

    private static List<ResourceStorage> createInitialStorages() {
        return Lists.newArrayList(
                new ResourceStorage(Iron, 0, MAX_STORAGE_CAPACITY),
                new ResourceStorage(Stone, 0, MAX_STORAGE_CAPACITY),
                new ResourceStorage(Food, 0, MAX_STORAGE_CAPACITY),
                new ResourceStorage(Oil, 0, MAX_STORAGE_CAPACITY)
            );
    }

    public synchronized void updateState() {
        this.state = new GameState(this.players);
    }

    public synchronized  GameState getState() {
        return state;
    }
}