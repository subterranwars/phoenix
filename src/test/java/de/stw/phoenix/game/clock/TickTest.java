package de.stw.phoenix.game.clock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class TickTest {

    @Test
    public void verifyDiff() {
        final Tick tick1 = new Tick(0, 5000);
        final Tick tick2 = new Tick(5000, 20000);

        // Tick 1 must be before tick 2, otherwise -> ERROR
        Assertions.assertThrows(IllegalArgumentException.class, () -> tick1.getDiff(tick2, TimeUnit.SECONDS));

        long diff = tick2.getDiff(tick1, TimeUnit.SECONDS);
        assertThat(diff, is(15l));
    }

}