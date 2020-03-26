package de.stw.phoenix.game.time;

public interface ClockService {
    Clock getCurrentClock();
    Tick getCurrentTick();
    Clock tick();
}
