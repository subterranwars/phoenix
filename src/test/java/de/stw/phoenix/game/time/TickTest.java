package de.stw.phoenix.game.time;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class TickTest {

    @Test
    public void verifyToMoment() {
        assertThat(new Tick(0, 5000).toMoment().asSeconds(), Matchers.is(5L));
    }

}