package de.stw.phoenix.game.engine.research.api.calculator;

import de.stw.phoenix.game.engine.research.api.ResearchRef;
import de.stw.phoenix.game.engine.research.api.Researchs;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.TimeDuration;

public interface ResearchTimeCalculator {
    TimeDuration calculateResearchTime(ResearchLevel level, Player player);

    default TimeDuration calculateResearchTime(ResearchRef research, int level, Player player) {
        return calculateResearchTime(new ResearchLevel(Researchs.findByRef(research), level), player);
    }
}
