package de.stw.phoenix.game.engine.research.api;

import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.player.impl.GameEventEntity;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@DiscriminatorValue("research")
public class ResearchEvent extends GameEventEntity {

    @OneToOne
    @JoinColumn(name="research_id")
    private Research research;

    @Column(name="level")
    private int levelToResearch;

    private ResearchEvent() {

    }

    public ResearchEvent(Player player, Research research, int levelToResearch, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        super(player,
                Progress.builder()
                .withValue(progress)
                .withDuration(estimatedDuration).build()
        , lastUpdate);
        this.research = Objects.requireNonNull(research);
        this.levelToResearch = levelToResearch;
    }

    public Research getResearch() {
        return research;
    }

    public int getLevelToResearch() {
        return levelToResearch;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
