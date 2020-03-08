package de.stw.phoenix.game.events;

import de.stw.phoenix.game.time.ArtificialClock;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Moment;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class GameEventTest {

    @Test
    public void verifyIsComplete() {
        final Clock clock = new ArtificialClock(5, TimeUnit.SECONDS);
        final Moment completionMoment = clock.getMoment(20, TimeUnit.SECONDS);
        final GameEvent gameEvent = () -> completionMoment;
        assertThat(gameEvent.isComplete(clock.getCurrentTick()), is(false)); // 0
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 5
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 10
        assertThat(gameEvent.isComplete(clock.nextTick()), is(false)); // 15
        assertThat(gameEvent.isComplete(clock.nextTick()), is(true)); // 20
        assertThat(gameEvent.isComplete(clock.nextTick()), is(true)); // 25
    }

}