package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.time.TimeDuration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ProductionValueTest {

    @Test
    public void verifyConversion() {
        assertThat(new ProductionValue(60, TimeDuration.ofMinutes(1)).convert(TimeUnit.SECONDS).getProductionPerTimeUnit(), is(1.0));
        assertThat(new ProductionValue(Resources.HQ_PRODUCTION_PER_HOUR, TimeDuration.ofHours(1)).convert(TimeUnit.SECONDS.MINUTES).getProductionPerTimeUnit(), is(1.0));
    }

}