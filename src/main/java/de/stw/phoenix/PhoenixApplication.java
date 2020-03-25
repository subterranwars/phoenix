package de.stw.phoenix;

import de.stw.phoenix.game.time.ArtificialClock;
import de.stw.phoenix.game.time.ClockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.support.TransactionOperations;

import java.util.concurrent.Executors;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "de.stw.phoenix")
public class PhoenixApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PhoenixApplication.class);

    @Autowired
    private ClockRepository clockRepository;

    @Autowired
    private TransactionOperations operations;

    // TODO MVR this looks bad
    @Bean
    public ArtificialClock createClock() {
        return operations.execute(status -> {
            final ArtificialClock clock = clockRepository.getOne("artificial");
            clock.getCurrentTick();
            return clock;
        });
    }

    @Bean(name = "myTaskExecutor")
    public TaskExecutor taskExecutor () {
        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(20));
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

}

