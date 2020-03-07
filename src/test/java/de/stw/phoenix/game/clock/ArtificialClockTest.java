package de.stw.phoenix.game.clock;

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
    public void verifyCalculateTick() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);

        // If the duration is smaller than the tick length, complete it on the next tick
        Tick tick = clock.getTick(1, TimeUnit.SECONDS);
        assertThat(tick.getEnd(), is(5000L));

        tick = clock.getTick(10, TimeUnit.SECONDS);
        assertThat(tick.getEnd(), is(10000L));

        clock.nextTick(); // 0 -> 5000
        tick = clock.getTick(1, TimeUnit.HOURS);
        assertThat(tick.getEnd(), is(60*60*1000 + 5000L));
    }

}