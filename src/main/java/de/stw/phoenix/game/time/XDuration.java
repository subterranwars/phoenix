package de.stw.phoenix.game.time;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

// TODO MVR find a better name, as java.time.Duration is already used
public class XDuration {

    @JsonProperty("buildTimeInSeconds")
    private final long seconds;

    public XDuration(java.time.Duration duration) {
        this(duration.getSeconds(), TimeUnit.SECONDS);
    }

    public XDuration(long value, TimeUnit timeUnit) {
        this.seconds = TimeUnit.SECONDS.convert(value, timeUnit);
    }

    public long getSeconds() {
        return seconds;
    }

    public static XDuration ofMinutes(int minutes) {
        return new XDuration(minutes, TimeUnit.MINUTES);
    }

    public static XDuration ofHours(int hours) {
        return new XDuration(hours, TimeUnit.HOURS);
    }

    public static XDuration ofSeconds(long seconds) {
        return new XDuration(seconds, TimeUnit.SECONDS);
    }


}
