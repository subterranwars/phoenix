package de.stw.phoenix.game.rest.player;

import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.ResourceProduction;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.engine.api.GameEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.construction.ConstructionEventDTO;
import de.stw.phoenix.game.rest.resources.ResourceSearchEventDTO;
import de.stw.phoenix.game.time.Tick;

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
    private final List<ResourceSite> resourceSites;

    public PlayerDTO(final ImmutablePlayer player, final List<ResourceProduction> resourceProduction, final Tick currentTick) {
        Objects.requireNonNull(player);
        this.buildings = player.getBuildings();
        this.events = player.getEvents().stream().map(e -> convert(e, currentTick)).collect(Collectors.toList());
        this.id = player.getId();
        this.name = player.getName();
        this.resourceProductions = Objects.requireNonNull(resourceProduction).stream()
                .map(production -> production.convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        this.resourceSites = Objects.requireNonNull(player.getResourceSites());
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

    // Converts the given gameEvent updating the time to subtract already passed ticks
    private static GameEventDTO convert(final GameEvent event, final Tick currentTick) {
        final long diffSeconds = currentTick.toMoment().getDiff(event.getUserCompletionMoment());
        if (event instanceof ConstructionEvent) {
            return new ConstructionEventDTO(diffSeconds, ((ConstructionEvent) event).getConstructionInfo());
        }
        if (event instanceof ResourceSearchEvent) {
            return new ResourceSearchEventDTO(diffSeconds, ((ResourceSearchEvent) event).getInfo());
        }
        throw new IllegalStateException("Cannot convert GameEvent of type " + event.getClass().getSimpleName());
    }

    public List<ResourceSite> getResourceSites() {
        return resourceSites;
    }
}
