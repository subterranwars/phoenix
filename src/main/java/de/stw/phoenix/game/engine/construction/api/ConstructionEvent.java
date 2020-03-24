package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.player.impl.GameEventEntity;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("construction")
public class ConstructionEvent extends GameEventEntity {

    @Embedded
    private ConstructionInfo constructionInfo;

    private ConstructionEvent() {
    }

    public ConstructionEvent(PlayerRef playerRef, ConstructionInfo constructionInfo, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        super(playerRef, Progress.builder()
                .withValue(progress)
                .withDuration(estimatedDuration).build(), lastUpdate);
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
