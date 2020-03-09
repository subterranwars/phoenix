package de.stw.phoenix.game.engine.modules.resources.api;

import de.stw.phoenix.game.player.api.ImmutableResourceStorage;

import java.util.Objects;

// TODO MVR provide builder?
public class ResourceSite {

    private final long id;
    private final ImmutableResourceStorage storage;
    private final int droneCount;

    public ResourceSite(final long id, final ImmutableResourceStorage storage, final int droneCount) {
        this.id = id;
        this.storage = Objects.requireNonNull(storage);
        this.droneCount = droneCount;
    }

    public long getId() {
        return id;
    }

    public ImmutableResourceStorage getStorage() {
        return storage;
    }

    public int getDroneCount() {
        return droneCount;
    }
}
