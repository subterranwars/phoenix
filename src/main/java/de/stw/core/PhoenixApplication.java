package de.stw.core;

import de.stw.core.clock.ArtificialClock;
import de.stw.core.clock.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "de.stw")
public class PhoenixApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PhoenixApplication.class);

    @Autowired
    private GameLoop loop;

    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private Clock clock;

    @Bean
    public Clock createClock() {
        return new ArtificialClock(10, TimeUnit.SECONDS);
    }

    @PostConstruct
    public void initGameLoop() {
        scheduler.schedule(new GameLoopScheduler(), Instant.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

    private class GameLoopScheduler implements Runnable {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            try {
                loop.loop();
            } finally {
                long end = System.currentTimeMillis();
                long diff = end - start;
                long expectedTickLength = clock.getCurrentTick().getDelta();
                long actualWaitTime = Math.max(expectedTickLength - diff, 0); // in case the diff is actuall longer than the wait time
                LOG.debug("Expected tick length: {} ms, actual wait time: {} ms", expectedTickLength, actualWaitTime);
                scheduler.schedule(new GameLoopScheduler(), Instant.now().plus(Duration.ofMillis(actualWaitTime)));
            }
        }
    }

}

