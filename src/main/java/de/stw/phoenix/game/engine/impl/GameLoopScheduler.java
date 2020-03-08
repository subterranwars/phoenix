package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.PhoenixApplication;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.engine.api.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;

@Service
public class GameLoopScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(PhoenixApplication.class);

    @Autowired
    private GameEngine gameEngine;

    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private Clock clock;

    @PostConstruct
    public void initGameLoop() {
        scheduler.schedule(this, Instant.now().plus(Duration.ofSeconds(10)));
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            gameEngine.loop();
        } finally {
            long end = System.currentTimeMillis();
            long diff = end - start;
            long expectedTickLength = clock.getCurrentTick().getDelta();
            long actualWaitTime = Math.max(expectedTickLength - diff, 0); // in case the diff is actual longer than the wait time
            LOG.debug("Expected tick length: {} ms, actual wait time: {} ms", expectedTickLength, actualWaitTime);
            scheduler.schedule(this, Instant.now().plus(Duration.ofMillis(actualWaitTime)));
        }
    }
}
