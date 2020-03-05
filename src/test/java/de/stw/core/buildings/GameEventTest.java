package de.stw.core.buildings;

import de.stw.core.clock.ArtificialClock;
import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GameEventTest {

    @Test
    public void verifyIsComplete() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);
        final Tick completionTick = clock.getTick(20, TimeUnit.SECONDS);
        final GameEvent gameEvent = () -> completionTick;
        assertThat(gameEvent.isComplete(clock.getCurrentTick()), is(false)); // 0
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 5
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 10
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 15
        assertThat(gameEvent.isComplete(clock.nextTick()), is(true)); // 20
        assertThat(gameEvent.isComplete(clock.nextTick()), is(true)); // 25
    }

}