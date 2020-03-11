package de.stw.phoenix.game.time;

import java.util.concurrent.TimeUnit;

public class TimeDuration {

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

    public long getSeconds() {
        return milliseconds / 1000;
    }

    public long getMinutes() {
        return getSeconds() / 60;
    }

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
