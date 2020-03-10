package de.stw.phoenix.game.player.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.engine.resources.api.Resource;

import java.util.Objects;

/**
 * Holds a single resource up to a maximum capacity
 * 
 * @author mvr, cjs
 */
public class ImmutableResourceStorage {
	private final Resource resource;
	private final double maxStorageCapacity;
	private final double amount;

	public ImmutableResourceStorage(final ImmutableResourceStorage input) {
		this(Objects.requireNonNull(input).getResource(), input.getAmount(), input.getCapacity());
	}

	public ImmutableResourceStorage(Resource resource, double amount, double maxStorageCapacity) {
		Preconditions.checkArgument(amount >= 0, "Amount must not be negative");
		Preconditions.checkArgument(maxStorageCapacity >= 0, "maxStorageCapacity must not be negative");
		this.resource = Objects.requireNonNull(resource);
		this.amount = Math.min(maxStorageCapacity, amount);
		this.maxStorageCapacity = maxStorageCapacity;
	}

	public Resource getResource() {
		return resource;
	}

	public double getAmount() {
		return amount;
	}

	public double getCapacity() {
		return maxStorageCapacity;
	}
}