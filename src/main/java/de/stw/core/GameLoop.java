package de.stw.core;

import com.google.common.collect.Lists;
import de.stw.core.clock.ArtificialClock;
import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameLoop {

    private final Clock clock;
    private final List<Player> players;

    private Resource iron = new Resource(1, "iron", 60);

    public GameLoop() {
        this.clock = new ArtificialClock(10, TimeUnit.SECONDS);
        this.players = Lists.newArrayList(
                new Player(1, "marskuh", Lists.newArrayList(new ResourceCount(iron, 0))),
                new Player(2, "fafner", Lists.newArrayList(new ResourceCount(iron, 0)))
        );
    }

    public void loop() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            printState();
            final Tick tick = clock.nextTick();
            for (Player eachPlayer : players) {
                for (ResourceCount eachResource : eachPlayer.getResources()) {
                    final float ratePerMillisecond = eachResource.getResource().getRatePerHour() / 60 / 1000;
                    final float resourceGrowth = tick.getDelta() * ratePerMillisecond;
                    eachResource.add(resourceGrowth);
                }
            }
            Thread.sleep(tick.getDelta());
        }
    }

    private void printState() {
        System.out.println("Tick: " + clock.getCurrentTick());
        for (Player eachPlayer : players) {
            System.out.println("Player " + eachPlayer.getName());
            for (ResourceCount resource : eachPlayer.getResources()) {
                System.out.println(" " + resource.getResource().getName() + ": " + resource.getAmount());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GameLoop().loop();
    }
}