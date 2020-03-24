package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.player.api.ResourceStorage;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResourceOverview {
    private final ResourceStorage storage;
    private final ProductionValue resourceProduction;
    private final Resource resource;

    public ResourceOverview(final ResourceStorage storage, final ProductionValue resourceProduction) {
        this.storage = Objects.requireNonNull(storage);
        this.resourceProduction = Objects.requireNonNull(resourceProduction);
        this.resource = Objects.requireNonNull(storage.getResource());
    }

    public ResourceStorage getStorage() {
        return storage;
    }

    public ProductionValue getResourceProduction() {
        return resourceProduction;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceOverview convert(TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        return new ResourceOverview(getStorage(), getResourceProduction().convert(timeUnit));
    }
}
