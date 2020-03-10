package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.api.GameEvent;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;

import java.util.List;
import java.util.Map;

public interface ImmutablePlayer extends PlayerRef {

    List<ImmutableResourceStorage> getResources();
    List<ResourceSite> getResourceSites();

    ImmutableResourceStorage getStorage(Resource resource);

    List<BuildingLevel> getBuildings();

    BuildingLevel getBuilding(Building building);

    boolean canAfford(Map<Resource, Integer> costs);

    List<GameEvent> getEvents();

    ConstructionEvent getConstructionEvent();

}
