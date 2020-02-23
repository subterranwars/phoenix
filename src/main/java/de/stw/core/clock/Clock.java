package de.stw.core.clock;

public interface Clock {
    Tick nextTick();
    Tick getCurrentTick();
}
