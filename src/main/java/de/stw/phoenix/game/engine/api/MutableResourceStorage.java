package de.stw.phoenix.game.engine.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;

import java.util.Objects;

public class MutableResourceStorage {
    private final Resource resource;
    private double maxStorageCapacity;
    private double amount;

    public MutableResourceStorage(ImmutableResourceStorage input) {
        Objects.requireNonNull(input);
        this.resource = input.getResource();
        this.maxStorageCapacity = input.getCapacity();
        this.amount = input.getAmount();
    }

    public MutableResourceStorage(Resource resource, double amount, double capacity) {
        this(new ImmutableResourceStorage(resource, amount, capacity));
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
//         TODO MVR should throw exception in case not "affordable"
        double nextStorageAmount = Math.max(0, amount - requestedAmount);
        double amountToRetrieve = amount - nextStorageAmount;
        this.amount = nextStorageAmount;
        return amountToRetrieve;
    }

    public Resource getResource() {
        return resource;
    }

    public double getCapacity() {
        return maxStorageCapacity;
    }

    public double getAmount() {
        return amount;
    }

    public ImmutableResourceStorage asImmutable() {
        return new ImmutableResourceStorage(this.resource, this.amount, this.maxStorageCapacity);
    }
}
