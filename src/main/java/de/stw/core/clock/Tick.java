package de.stw.core.clock;

import com.google.common.base.MoreObjects;

import java.util.Objects;

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