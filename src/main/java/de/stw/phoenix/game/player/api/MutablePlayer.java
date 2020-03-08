package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSite;
import de.stw.phoenix.game.events.GameEvent;

import java.util.List;
import java.util.Map;

public interface MutablePlayer extends ImmutablePlayer {
    void setBuilding(BuildingLevel buildingLevel);

    <T extends GameEvent> void addEvent(T event);
    void removeEvent(GameEvent event);
    void removeEvents(List<GameEvent> completedEvents);

    void addResourceSite(ResourceSite resourceSite);
    void addResources(Resource resource, double amount);
    void removeResources(Map<Resource, Integer> costs);

    ImmutablePlayer asImmutable();

}
