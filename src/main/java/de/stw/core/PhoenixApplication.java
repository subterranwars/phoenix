package de.stw.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "de.stw")
public class PhoenixApplication {

    @Autowired
    private GameLoop loop;

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

    // TODO MVR maybe this should be dynamic
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void gameLoop() {
        loop.loop();
    }

}

