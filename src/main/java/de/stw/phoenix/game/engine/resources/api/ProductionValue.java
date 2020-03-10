package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ProductionValue {
    private final TimeUnit timeUnit;
    private final double productionPerTimeUnit;

    public ProductionValue(final long productionPerDuration, final TimeDuration duration) {
        this(productionPerDuration / (double) Objects.requireNonNull(duration).getSeconds(), TimeUnit.SECONDS);
    }

    public ProductionValue(double productionPerTimeUnit, TimeUnit timeUnit) {
        this.productionPerTimeUnit = productionPerTimeUnit;
        this.timeUnit = Objects.requireNonNull(timeUnit);
    }

    public ProductionValue convert(final TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        long convertedSeconds = TimeUnit.SECONDS.convert(1, timeUnit);
        double convertedProductionPerTimeUnit = productionPerTimeUnit * convertedSeconds;
        return new ProductionValue(convertedProductionPerTimeUnit, timeUnit);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public double getProductionPerTimeUnit() {
        return productionPerTimeUnit;
    }
}
