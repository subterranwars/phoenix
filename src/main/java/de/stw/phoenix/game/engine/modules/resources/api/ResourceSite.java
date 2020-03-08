package de.stw.phoenix.game.engine.modules.resources.api;

import de.stw.phoenix.game.data.resources.Resource;

import java.util.Objects;

public class ResourceSite {

    private final Resource resource;
    private final Long amount;

    public ResourceSite(Resource resource, long amount) {
        this.resource = Objects.requireNonNull(resource);
        this.amount = Objects.requireNonNull(amount);
    }

    public Resource getResource() {
        return resource;
    }

    public Long getAmount() {
        return amount;
    }
}
