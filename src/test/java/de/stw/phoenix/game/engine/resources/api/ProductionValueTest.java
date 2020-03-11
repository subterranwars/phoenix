package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.time.TimeDuration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

class ProductionValueTest {

    @Test
    public void verifyConversion() {
        assertThat(new ProductionValue(60, TimeDuration.ofMinutes(1)).convert(TimeUnit.SECONDS).getProductionPerTimeUnit(), is(1.0));
        assertThat(new ProductionValue(Resources.HQ_PRODUCTION_PER_HOUR, TimeDuration.ofHours(1)).convert(TimeUnit.SECONDS.MINUTES).getProductionPerTimeUnit(), is(1.0));
    }

    @Test
    public void verifyConversion2() {
        ProductionValue productionValue = new ProductionValue(60, TimeDuration.ofHours(1));
        assertThat(productionValue.convert(TimeUnit.HOURS).getProductionPerTimeUnit(), is(60.0));
        assertThat(productionValue.convert(TimeUnit.SECONDS).getProductionPerTimeUnit(), closeTo(0.01666, 0.0001));
        assertThat(productionValue.convert(TimeUnit.MILLISECONDS).getProductionPerTimeUnit(), closeTo(0.00001666, 0.0001));
    }

}