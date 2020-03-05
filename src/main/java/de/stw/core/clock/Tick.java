package de.stw.core.clock;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

// TODO MVr rework tick concept, only use for gameloop diff and clock
public class Tick {
    private final long startTime;
    private final long endTime;

    public Tick(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getDelta() {
        return endTime - startTime;
    }

    public long getStart() {
        return startTime;
    }

    public long getEnd() {
        return endTime;
    }

    public boolean isGreaterOrEqual(Tick completionTick) {
        return this.endTime >= completionTick.getEnd();
    }

    public long getDiff(Tick tickToCompare, TimeUnit timeUnit) {
        if (tickToCompare.isGreaterOrEqual(this)) {
            throw new IllegalArgumentException("tickToCompare must be AFTER this tick");
        }
        long diff = this.endTime - tickToCompare.endTime;
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Tick tick = (Tick) o;
        return startTime == tick.startTime &&
                endTime == tick.endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}
