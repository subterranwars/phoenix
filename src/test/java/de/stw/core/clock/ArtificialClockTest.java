package de.stw.core.clock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ArtificialClockTest {

    @Test
    public void verifyArtificialClock() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);
        assertThat(clock.getCurrentTick(), is(new Tick(0, 0)));
        assertThat(clock.nextTick(), is(new Tick(0, 5000)));
        assertThat(clock.nextTick(), is(new Tick(5000, 10000)));
    }

}