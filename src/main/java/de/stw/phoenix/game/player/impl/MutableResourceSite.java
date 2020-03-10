package de.stw.phoenix.game.player.impl;

import de.stw.phoenix.game.engine.modules.resources.api.ResourceSite;
import de.stw.phoenix.game.player.api.MutableResourceStorage;

public class MutableResourceSite {

    private final long id;
    private final MutableResourceStorage storage;
    private int droneCount;

    public MutableResourceSite(ResourceSite site) {
        this.id = site.getId();
        this.storage = new MutableResourceStorage(site.getStorage());
        this.droneCount = site.getDroneCount();
    }

    public long getId() {
        return id;
    }

    public void setDroneCount(int droneCount) {
        this.droneCount = droneCount;
    }

    public MutableResourceStorage getStorage() {
        return storage;
    }

    public ResourceSite asImmutable() {
        return new ResourceSite(id, storage.asImmutable(), droneCount);
    }

}
