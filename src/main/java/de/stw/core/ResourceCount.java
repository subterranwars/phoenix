package de.stw.core;

import java.util.Objects;

public class ResourceCount {
    private Resource resource;
    private float amount;

    public ResourceCount(Resource resource, int amount) {
        this.resource = Objects.requireNonNull(resource);
        this.amount = amount;
    }

    public Resource getResource() {
        return resource;
    }

    public float getAmount() {
        return amount;
    }

    public void add(float addMe) {
        this.amount += addMe;
    }
}
