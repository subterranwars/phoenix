package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EnergyOverview {

    private final ProductionValue production;

    public EnergyOverview(double totalProduction) {
        this(new ProductionValue(totalProduction, TimeDuration.ofHours(1)));
    }

    private EnergyOverview(final ProductionValue productionValue) {
        this.production = Objects.requireNonNull(productionValue);
    }

    public ProductionValue getProduction() {
        return production;
    }

    public EnergyOverview convert(TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        return new EnergyOverview(production.convert(timeUnit));
    }
}

