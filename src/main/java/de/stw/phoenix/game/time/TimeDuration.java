package de.stw.phoenix.game.time;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

public class TimeDuration {

    @JsonProperty("buildTimeInSeconds")
    private final long seconds;

    public TimeDuration(java.time.Duration duration) {
        this(duration.getSeconds(), TimeUnit.SECONDS);
    }

    public TimeDuration(long value, TimeUnit timeUnit) {
        this.seconds = TimeUnit.SECONDS.convert(value, timeUnit);
    }

    public long getSeconds() {
        return seconds;
    }

    public long getHours() {
        return seconds / 3600;
    }

    public long getMinutes() {
        return seconds / 60;
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
