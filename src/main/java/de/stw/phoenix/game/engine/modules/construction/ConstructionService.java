package de.stw.phoenix.game.engine.modules.construction;

import de.stw.phoenix.game.data.buildings.Building;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ConstructionService {
    List<ConstructionInfo> listConstructions(ImmutablePlayer player);

    void build(ImmutablePlayer player, Building building);
}
