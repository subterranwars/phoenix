package de.stw.phoenix.game.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

class MomentTest {

    @Test
    public void verifyDiff() {
        final Moment moment1 = new Tick(0, 5000).toMoment();
        final Moment moment2 = new Tick(5000, 20000).toMoment();

        assertThat(moment1.getDiff(moment2), is(15L));
        assertThat(moment2.getDiff(moment1), is(-15L));
    }

}