package de.stw.phoenix.game.rest;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.engine.modules.construction.ConstructionEvent;
import de.stw.phoenix.game.engine.modules.resources.ResourceProduction;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.rest.construction.ConstructionEventDTO;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerDTO {
    private long id;
    private String name;
    private List<ResourceProduction> resourceProductions;
    private List<BuildingLevel> buildings;
    private List<GameEventDTO> events;

    public PlayerDTO(final ImmutablePlayer player, final List<ResourceProduction> resourceProduction, final Tick currentTick) {
        Objects.requireNonNull(player);
        this.resourceProductions = Objects.requireNonNull(resourceProduction);
        this.buildings = player.getBuildings();
        this.events = player.getEvents().stream().map(e -> convert(e, currentTick)).collect(Collectors.toList());
        this.id = player.getId();
        this.name = player.getName();
    }

    private static GameEventDTO convert(final GameEvent e, final Tick currentTick) {
        if (e instanceof ConstructionEvent) {
            return new ConstructionEventDTO(e.getCompletionTick().getDiff(currentTick, TimeUnit.SECONDS), ((ConstructionEvent) e).getConstructionInfo());
        }
        throw new IllegalStateException("Cannot convert GameEvent of type " + e.getClass().getSimpleName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ResourceProduction> getResourceProductions() {
        return resourceProductions;
    }

    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    public List<GameEventDTO> getEvents() {
        return events;
    }
}
