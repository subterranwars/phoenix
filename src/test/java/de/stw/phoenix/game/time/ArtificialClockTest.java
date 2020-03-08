package de.stw.phoenix.game.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ArtificialClockTest {

    @Test
    public void verifyArtificialClock() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);
        assertThat(clock.getCurrentTick(), is(new Tick(0, 0)));
        assertThat(clock.nextTick(), is(new Tick(0, 5000)));
        assertThat(clock.nextTick(), is(new Tick(5000, 10000)));
    }

    @Test
    public void verifyCalculateMoment() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);

        // If the duration is smaller than the tick length, complete it on the next tick
        assertThat(clock.getMoment(1, TimeUnit.SECONDS).asSeconds(), is(5L));
        assertThat(clock.getMoment(TimeDuration.ofSeconds(1)).asSeconds(), is(5L));

        assertThat(clock.getMoment(10, TimeUnit.SECONDS).asSeconds(), is(10L));
        assertThat(clock.getMoment(TimeDuration.ofSeconds(10)).asSeconds(), is(10L));

        clock.nextTick(); // 0 -> 5000
        assertThat(clock.getMoment(1, TimeUnit.HOURS).asSeconds(), is(60*60 + 5L));
        assertThat(clock.getMoment(TimeDuration.ofHours(1)).asSeconds(), is(60*60 + 5L));
    }

}