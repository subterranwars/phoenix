package de.stw.phoenix.game.engine.research.api;

import de.stw.phoenix.game.player.impl.Player;

import java.util.List;

public interface ResearchService {
    List<ResearchInfo> listResearchs(Player player);

    void research(Player player, Research research);
}
