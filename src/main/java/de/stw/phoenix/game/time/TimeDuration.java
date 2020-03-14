package de.stw.phoenix.game.time;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.concurrent.TimeUnit;

public class TimeDuration {

    public static final TimeDuration UNKNOWN = new TimeDuration(-1, TimeUnit.MILLISECONDS);

    private final long milliseconds;

    public TimeDuration(java.time.Duration duration) {
        this(duration.getSeconds(), TimeUnit.MILLISECONDS);
    }

    public TimeDuration(long value, TimeUnit timeUnit) {
        this.milliseconds = TimeUnit.MILLISECONDS.convert(value, timeUnit);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @JsonIgnore
    public long getSeconds() {
        return milliseconds / 1000;
    }

    @JsonIgnore
    public long getMinutes() {
        return getSeconds() / 60;
    }

    @JsonIgnore
    public long getHours() {
        return getSeconds() / 3600;
    }

    public static TimeDuration ofMinutes(int minutes) {
        return new TimeDuration(minutes, TimeUnit.MINUTES);
    }

    public static TimeDuration ofHours(int hours) {
        return new TimeDuration(hours, TimeUnit.HOURS);
    }

    public static TimeDuration ofSeconds(long seconds) {
        return new TimeDuration(seconds, TimeUnit.SECONDS);
    }

}
