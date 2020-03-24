package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.PhoenixApplication;
import de.stw.phoenix.game.engine.api.GameEngine;
import de.stw.phoenix.game.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

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

    @Autowired
    private TransactionOperations transactionOperations;

    @PostConstruct
    public void initGameLoop() {
        scheduler.schedule(this, Instant.now().plus(Duration.ofSeconds(10)));
    }

    @Override
    public void run() {
        // TODO MVR rework clock concept, as this is really really weird (-:
        // TODO MVR also make sure the clock is actually ticking when persisting
        transactionOperations.execute(status -> {
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
            return null;
        });

    }
}
