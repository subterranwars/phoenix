package de.stw.phoenix.game.time;

import de.stw.phoenix.game.time.XDuration;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class XDurationTest {

    @Test
    public void verifyCreation() {
        assertThat(XDuration.ofMinutes(5).getSeconds(), Matchers.is(300L));
        assertThat(XDuration.ofSeconds(5).getSeconds(), Matchers.is(5L));
        assertThat(XDuration.ofHours(5).getSeconds(), Matchers.is(18000L));
    }

}