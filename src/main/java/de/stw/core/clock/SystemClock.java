package de.stw.core.clock;

public class SystemClock implements Clock {

    private long currentTime;
    private Tick tick;

    public SystemClock() {
        this(System.currentTimeMillis());
    }

    public SystemClock(long currentTime) {
        this.currentTime = currentTime;
        this.tick = new Tick(currentTime, currentTime);
    }

    @Override
    public Tick nextTick() {
        this.tick = new Tick(currentTime, System.currentTimeMillis());
        return tick;
    }

    @Override
    public Tick getCurrentTick() {
        return tick;
    }

}
