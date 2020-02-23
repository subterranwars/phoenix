package de.stw.core;

import com.google.common.collect.Lists;
import de.stw.core.clock.ArtificialClock;
import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static de.stw.core.resources.Resources.IRON;
import static de.stw.core.resources.Resources.MAX_STORAGE_CAPACITY;

public class GameLoop {

    private final Clock clock;
    private final List<Player> players;
    private final List<ResourceProduction> processes;

    public GameLoop() {
        this.clock = new ArtificialClock(10, TimeUnit.SECONDS);
        this.players = Lists.newArrayList(
                new Player(1, "marskuh", Lists.newArrayList(new ResourceStorage(IRON, 0, MAX_STORAGE_CAPACITY))),
                new Player(2, "fafner", Lists.newArrayList(new ResourceStorage(IRON, 0, MAX_STORAGE_CAPACITY)))
        );
        this.processes = players.stream()
				.flatMap(p -> p.getResources().stream())
				.map(storage -> new ResourceProduction(storage, 60))
				.collect(Collectors.toList());
    }

    public void loop() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            printState();
			      final Tick tick = clock.nextTick();
            for (ResourceProduction production : processes) {
            	production.update(tick);
			      }
            Thread.sleep(tick.getDelta());
        }
    }

    private void printState() {
        System.out.println("Tick: " + clock.getCurrentTick());
        for (Player eachPlayer : players) {
            System.out.println("Player " + eachPlayer.getName());
            for (ResourceStorage resourceStorage : eachPlayer.getResources()) {
                System.out.println(" " + resourceStorage.getResource().getName() + ": " + resourceStorage.getAmount());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GameLoop().loop();
    }
}