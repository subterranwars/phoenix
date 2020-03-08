package de.stw.phoenix.game.time;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class TimeDurationTest {

    @Test
    public void verifyCreation() {
        assertThat(TimeDuration.ofMinutes(5).getSeconds(), Matchers.is(300L));
        assertThat(TimeDuration.ofSeconds(5).getSeconds(), Matchers.is(5L));
        assertThat(TimeDuration.ofHours(5).getSeconds(), Matchers.is(18000L));
    }

}