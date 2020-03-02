package de.stw.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class PhoenixApplication {

    private GameLoop loop = new GameLoop();

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void gameLoop() {
        loop.loop();
    }

}

