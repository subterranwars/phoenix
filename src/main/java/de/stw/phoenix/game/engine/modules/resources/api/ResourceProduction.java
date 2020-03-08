package de.stw.phoenix.game.engine.modules.resources.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResourceProduction {
	private final ImmutableResourceStorage storage;
	private final double productionPerHour;
	private final PlayerRef playerRef;

	public ResourceProduction(final PlayerRef playerRef, final ImmutableResourceStorage storage, double productionPerHour) {
		Objects.requireNonNull(storage);
		Preconditions.checkArgument(productionPerHour >= 0);

		this.playerRef = Objects.requireNonNull(playerRef);
		this.storage = Objects.requireNonNull(storage);
		this.productionPerHour = productionPerHour;
	}

	public PlayerRef getPlayerRef() {
		return playerRef;
	}

	public ImmutableResourceStorage getStorage() {
		return storage;
	}

	public ResourceProduction convert(TimeUnit timeUnit) {
		long units = timeUnit.convert(1, TimeUnit.HOURS);
		double productionPerTimeUnit = productionPerHour / units;
		return new ResourceProduction(playerRef, new ImmutableResourceStorage(storage), productionPerTimeUnit);
	}

	public double getProductionValue() {
		return productionPerHour;
	}
}