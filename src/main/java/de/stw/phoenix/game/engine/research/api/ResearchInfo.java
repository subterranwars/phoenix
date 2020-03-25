package de.stw.phoenix.game.engine.research.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public class ResearchInfo {

    private final Research research;
    private final int levelToResearch;
    private final TimeDuration researchTime;

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
