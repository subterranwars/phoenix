package de.stw.core.resources;

import com.google.common.base.Preconditions;
import de.stw.core.clock.Tick;
import de.stw.core.clock.TimeConstants;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResourceProduction {
	private final ResourceStorage storage;
	private final double productionPerHour;

	public ResourceProduction(final ResourceStorage storage, double productionPerHour) {
		Objects.requireNonNull(storage);
		Preconditions.checkArgument(productionPerHour >= 0);

		this.storage = Objects.requireNonNull(storage);
		this.productionPerHour = productionPerHour;
	}

	public void update(Tick tick) {
		long ms = tick.getDelta();
		double amountToProduceInTick = productionPerHour / TimeConstants.MILLISECONDS_PER_HOUR * ms;
		storage.store(amountToProduceInTick);
	}
	public ResourceStorage getStorage() {
		return storage;
	}

	public ResourceProduction convert(TimeUnit timeUnit) {
		long units = timeUnit.convert(1, TimeUnit.HOURS);
		double productionPerTimeUnit = productionPerHour / units;
		return new ResourceProduction(new ResourceStorage(storage), productionPerTimeUnit);
	}

	public double getProductionValue() {
		return productionPerHour;
	}
}