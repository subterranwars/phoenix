package de.stw.phoenix.game.player.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class Progress {

    @Embedded
    private TimeDuration duration;

    @Column(name = "progress", nullable = false)
    private double currentValue; // 0 -> 1

    private Progress() {

    }

    private Progress(Builder builder) {
        Objects.requireNonNull(builder);
        this.duration = Objects.requireNonNull(builder.timeDuration);
        this.currentValue = Objects.requireNonNull(builder.value);
    }

    public TimeDuration getDuration() {
        return duration;
    }

    public double getValue() {
        return currentValue;
    }

    public boolean isFinished() {
        return currentValue >= 1.0;
    }

    // Is the end known?
    public boolean isIndeterminate() {
        return duration == TimeDuration.UNKNOWN;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private double value = 0;
        private TimeDuration timeDuration;

        private Builder() {}

        public Builder withValue(double currentValue) {
            Preconditions.checkArgument(currentValue >= 0);
            this.value = currentValue;
            return this;
        }

        public Builder withUnknownDuration() {
            return withDuration(TimeDuration.UNKNOWN);
        }

        public Builder withDuration(TimeDuration timeDuration) {
            Objects.requireNonNull(timeDuration);
            this.timeDuration = timeDuration;
            return this;
        }

        public Progress build() {
            return new Progress(this);
        }
    }
}
