package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.player.impl.MutableResourceSite;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MutablePlayer extends ImmutablePlayer {
    void setBuilding(BuildingLevel buildingLevel);


    <T extends GameEvent> void addEvent(T event);
    void removeEvent(GameEvent event);
    void removeEvents(List<GameEvent> completedEvents);

    void addResourceSite(ResourceSite resourceSite);
    void removeResourceSite(MutableResourceSite resourceSite);
    Optional<MutableResourceSite> getResourceSite(long resourceSiteId);

    void updateResourceStorage(long maxStorage);

    void addResources(Resource resource, double amount);
    void removeResources(Resource resource, double consumptionPerTick);
    void removeResources(Map<Resource, Integer> costs);

    ImmutablePlayer asImmutable();

}
