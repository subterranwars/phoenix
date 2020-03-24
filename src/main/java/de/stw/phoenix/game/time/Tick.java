package de.stw.phoenix.game.time;

import com.google.common.base.MoreObjects;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Embeddable
public class Tick {

    private long startTime;
    private long endTime;

    private Tick() {

    }

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
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    // Each tick describes a moment at the endTime
    public Moment toMoment() {
        return new Moment(endTime, TimeUnit.MILLISECONDS);
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}
