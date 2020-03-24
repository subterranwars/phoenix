package de.stw.phoenix.game.engine.resources.impl;

import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.player.impl.GameEventEntity;
import de.stw.phoenix.game.time.Moment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@DiscriminatorValue("resource_search")
public class ResourceSearchEvent extends GameEventEntity {

    @OneToOne
    @JoinColumn(name="resource_id")
    private Resource resource;

    private ResourceSearchEvent() {

    }

    public ResourceSearchEvent(PlayerRef playerRef, Resource resource, Moment lastUpdate) {
        super(playerRef, Progress.builder().withUnknownDuration().build(), lastUpdate);
        this.resource = Objects.requireNonNull(resource);
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }

}
