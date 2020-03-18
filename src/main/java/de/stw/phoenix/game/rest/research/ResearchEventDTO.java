package de.stw.phoenix.game.rest.research;

import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.GameEventTypes;

import java.util.Objects;

public class ResearchEventDTO extends GameEventDTO {
    private Research research;
    private int level;

    public ResearchEventDTO(Progress progress, ResearchInfo researchInfo) {
        super(GameEventTypes.Research, progress);
        Objects.requireNonNull(researchInfo);
        this.research = Objects.requireNonNull(researchInfo.getResearch());
        this.level = researchInfo.getLevelToResearch();
    }

    public Research getResearch() {
        return research;
    }

    public int getLevel() {
        return level;
    }
}
