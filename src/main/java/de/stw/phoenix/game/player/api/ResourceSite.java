package de.stw.phoenix.game.player.api;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

// TODO MVR provide builder?
@Entity
@Table(name="player_resource_sites")
public class ResourceSite {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private ResourceStorage storage;

    @Column(name="drone_count")
    private int droneCount;

    private ResourceSite() {

    }

    // TODO MVR use builder pattern instead
    public ResourceSite(final ResourceStorage storage, final int droneCount) {
        this.storage = Objects.requireNonNull(storage);
        this.droneCount = droneCount;
    }

    public long getId() {
        return id;
    }

    public ResourceStorage getStorage() {
        return storage;
    }

    public int getDroneCount() {
        return droneCount;
    }

    public void setDroneCount(int droneCount) {
        Preconditions.checkArgument(droneCount >= 0);
        this.droneCount = droneCount;
    }
}
