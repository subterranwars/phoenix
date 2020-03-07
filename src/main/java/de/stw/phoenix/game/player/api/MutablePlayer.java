package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.engine.modules.construction.ConstructionEvent;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;
import java.util.Map;

public interface MutablePlayer extends ImmutablePlayer {
    void setBuilding(BuildingLevel buildingLevel);

    void addConstruction(ConstructionEvent constructionEvent);

    void removeEvent(GameEvent event);

    void removeEvents(List<GameEvent> completedEvents);

    void addResources(Resource resource, double amount);
    void removeResources(Map<Resource, Integer> costs);

    ImmutablePlayer asImmutable();
}
