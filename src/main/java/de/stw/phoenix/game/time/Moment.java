package de.stw.phoenix.game.time;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Defines a certain point in time, relative to the current {@link Tick}.
 *
 * @author mvrueden
 */
@Embeddable
public class Moment {

    @Column(name="moment")
    private long moment;

    @Column(name="unit")
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    private Moment() {

    }

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
