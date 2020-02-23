package de.stw.core.clock;

import java.util.concurrent.TimeUnit;

public class ArtificialClock implements Clock {

    private Tick tick;
    private final long tickLength;
    private final TimeUnit tickUnit;

    public ArtificialClock(long tickLength, TimeUnit tickUnit) {
        this.tickLength = tickLength;
        this.tickUnit = tickUnit;
        this.tick = new Tick(0, 0);
    }

    @Override
    public Tick nextTick() {
        this.tick = new Tick(tick.getEnd(), tick.getEnd() + TimeUnit.MILLISECONDS.convert(tickLength, tickUnit));
        return tick;
    }

    @Override
    public Tick getCurrentTick() {
        return tick;
    }
}
