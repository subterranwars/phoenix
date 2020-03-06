package de.stw.phoenix;

import de.stw.phoenix.game.clock.ArtificialClock;
import de.stw.phoenix.game.clock.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "de.stw")
public class PhoenixApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PhoenixApplication.class);

    @Bean
    public Clock createClock() {
        return new ArtificialClock(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

}

