package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.research.api.Research;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

// TODO MVR identical to BuildingLevel
@Entity
@Table(name="player_researchs")
public class ResearchLevel {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="research_id", nullable = false)
    private Research research;

    @Column(name="level")
    private int level;

    private ResearchLevel() {

    }

    public ResearchLevel(final Research research, int level) {
        this.research = Objects.requireNonNull(research);
        this.level = level;
    }

    public Research getResearch() {
        return research;
    }

    public int getLevel() {
        return level;
    }

    public ResearchLevel next() {
        return new ResearchLevel(research, level + 1);
    }
}
