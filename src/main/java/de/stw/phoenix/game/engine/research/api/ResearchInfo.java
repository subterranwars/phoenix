package de.stw.phoenix.game.engine.research.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.Objects;

@Embeddable
public class ResearchInfo {

    @OneToOne
    @JoinColumn(name="research_id")
    private Research research;

    @Column(name="level")
    private int levelToResearch;

    @Transient
    private TimeDuration researchTime;

    private ResearchInfo() {

    }

    public ResearchInfo(final Research research, int levelToResearch, TimeDuration researchTime) {
        Preconditions.checkArgument(levelToResearch > 0);
        this.research = Objects.requireNonNull(research);
        this.researchTime = Objects.requireNonNull(researchTime);
        this.levelToResearch = levelToResearch;
    }

    public ResearchInfo(ResearchLevel researchLevel, TimeDuration constructionTime) {
        this(Objects.requireNonNull(researchLevel).getResearch(), researchLevel.getLevel(), constructionTime);
    }

    public Research getResearch() {
        return research;
    }

    public int getLevelToResearch() {
        return levelToResearch;
    }

    public TimeDuration getResearchTime() {
        return researchTime;
    }
}
