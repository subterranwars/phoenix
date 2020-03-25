package de.stw.phoenix.game.engine.research.api;

import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.player.impl.GameEventEntity;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("research")
public class ResearchEvent extends GameEventEntity {

    @Embedded
    private ResearchInfo researchInfo;

    private ResearchEvent() {

    }

    public ResearchEvent(Player player, ResearchInfo researchInfo, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        super(player,
                Progress.builder()
                .withValue(progress)
                .withDuration(estimatedDuration).build()
        , lastUpdate);
        this.researchInfo = Objects.requireNonNull(researchInfo);
    }


    public ResearchInfo getResearchInfo() {
        return researchInfo;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
