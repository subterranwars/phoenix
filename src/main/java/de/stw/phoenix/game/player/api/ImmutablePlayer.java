package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.energy.PlayerModifier;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ImmutablePlayer extends PlayerRef {

    List<ImmutableResourceStorage> getResources();
    List<ResourceSite> getResourceSites();

    ImmutableResourceStorage getStorage(Resource resource);

    List<BuildingLevel> getBuildings();

    BuildingLevel getBuilding(Building building);

    boolean canAfford(Map<Resource, Double> costs);

    List<GameEvent> getEvents();
    <T extends GameEvent> List<T> findEvents(Class<T> eventType);
    <T extends GameEvent> Optional<T> findSingleEvent(Class<T> eventType);

    List<PlayerModifier> getModifiers();
    <T extends PlayerModifier> List<T> findModifier(Class<T> modifierType);

    long getTotalDroneCount();

}
