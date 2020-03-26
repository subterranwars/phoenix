package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.player.impl.Player;

import java.util.List;

public interface ConstructionService {
    List<ConstructionInfo> listConstructions(Player player);

    void build(Player player, Building building);
}
