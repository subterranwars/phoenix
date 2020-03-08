package de.stw.phoenix.game.time;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Defines a certain point in time, relative to the current {@link Tick}.
 *
 * @author mvrueden
 */
public class Moment {
    private final long moment;
    private final TimeUnit unit;

    public Moment(long value, TimeUnit unit) {
        this.moment = value;
        this.unit = Objects.requireNonNull(unit);
    }

    @JsonGetter("seconds")
    public long asSeconds() {
        return TimeUnit.SECONDS.convert(moment, unit);
    }

    public boolean isGreaterOrEqual(Moment completionMoment) {
        return asSeconds() >= completionMoment.asSeconds();
    }

    // Returns the difference in seconds
    public long getDiff(Moment momentToCompare) {
        long diff = momentToCompare.asSeconds() - asSeconds();
        return diff;
    }

}
