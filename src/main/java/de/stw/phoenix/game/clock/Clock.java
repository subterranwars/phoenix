package de.stw.phoenix.game.clock;

import java.util.concurrent.TimeUnit;

public interface Clock {
    Tick nextTick();
    Tick getCurrentTick();
    Tick getTick(long duration, TimeUnit timeUnit);
}
