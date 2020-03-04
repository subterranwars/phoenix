package de.stw.core.resources;

import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 * Holds a single resource up to a maximum capacity
 * 
 * @author mvr, cjs
 */
public class ResourceStorage {
	private Resource resource;
	private double maxStorageCapacity;
	private double amount;

	public ResourceStorage(final ResourceStorage input) {
		this(Objects.requireNonNull(input).getResource(), input.getAmount(), input.getCapacity());
	}

	public ResourceStorage(Resource resource, double amount, double maxStorageCapacity) {
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

	/**
	 * Stores given amount to this storage up until {@link #maxStorageCapacity} is
	 * reached. Additional added resources will be lost.
	 * 
	 * @param amountToStore
	 */
	public void store(double amountToStore) {
		Preconditions.checkArgument(amountToStore > 0);
		this.amount = Math.min(maxStorageCapacity, amount + amountToStore);
	}

	/**
	 * Retrieves the requested amount from this storage.
	 * 
	 * @param requestedAmount
	 * @return If this storage does not contain the requested amount, the remaining amount is returned; otherwise requested amount
	 */
	public double retrieve(double requestedAmount) {
		// TODO MVR should throw exception in case not "affordable"
		double nextStorageAmount = Math.max(0, amount - requestedAmount);
		double amountToRetrieve = amount - nextStorageAmount;
		this.amount = nextStorageAmount;
		return amountToRetrieve;
	}
}