package de.stw.phoenix.game.time;

import java.util.concurrent.TimeUnit;

// TODO MVR Make threadsafe.... when invoking getTick(int, TimeUnit) the next tick may be invoked and therefore it is not threadsafe
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
    public synchronized Tick nextTick() {
        this.tick = new Tick(tick.getEnd(), tick.getEnd() + TimeUnit.MILLISECONDS.convert(tickLength, tickUnit));
        return tick;
    }

    @Override
    public synchronized Tick getCurrentTick() {
        return tick;
    }

    @Override
    public synchronized Moment getMoment(long duration, TimeUnit timeUnit) {
        final Tick currentTick = getCurrentTick();
        final long durationInMs = TimeUnit.MILLISECONDS.convert(duration, timeUnit);
        final long tickLengthInMs = TimeUnit.MILLISECONDS.convert(tickLength, tickUnit);
        final long numberOfTicks = (long) Math.ceil(durationInMs / (float) tickLengthInMs);
        final long endTick = tickLengthInMs * numberOfTicks + currentTick.getEnd();
        return new Moment(endTick, TimeUnit.MILLISECONDS);
    }
}
