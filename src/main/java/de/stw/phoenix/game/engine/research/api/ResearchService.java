package de.stw.phoenix.game.engine.research.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ResearchService {
    List<ResearchInfo> listResearchs(ImmutablePlayer player);

    void research(ImmutablePlayer player, Research research);
}
