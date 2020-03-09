package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.data.buildings.Building;
import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.engine.modules.construction.ConstructionEvent;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSite;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.time.Tick;

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

    List<GameEvent> getEvents(Tick tick);

}
