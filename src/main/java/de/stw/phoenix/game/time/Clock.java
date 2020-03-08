package de.stw.phoenix.game.time;

import java.util.concurrent.TimeUnit;

public interface Clock {
    Tick nextTick();
    Tick getCurrentTick();
    Moment getMoment(long duration, TimeUnit timeUnit);

    default Moment getMoment(TimeDuration duration) {
        return getMoment(duration.getSeconds(), TimeUnit.SECONDS);
    }
}
