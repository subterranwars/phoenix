package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.research.api.Research;

import java.util.Objects;

// TODO MVR identical to BuildingLevel
public class ResearchLevel {
    private final Research research;
    private final int level;

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
