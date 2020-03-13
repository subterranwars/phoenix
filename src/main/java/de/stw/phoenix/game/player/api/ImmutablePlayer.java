package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.energy.PlayerModifier;
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

    // TODO MVR add a findEvent(...) method
    ConstructionEvent getConstructionEvent();

    List<PlayerModifier> getModifiers();
    <T extends PlayerModifier> List<T> findModifier(Class<T> modifierType);

}
