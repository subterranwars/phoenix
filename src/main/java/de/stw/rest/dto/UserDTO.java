package de.stw.rest.dto;

import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.ConstructionEvent;
import de.stw.core.buildings.GameEvent;
import de.stw.core.clock.Tick;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UserDTO {
    private int id;
    private String name;
    private List<ResourceProduction> resourceProductions;
    private List<BuildingLevel> buildings;
    private List<GameEventDTO> events;

    public UserDTO(final User user, final Tick currentTick) {
        Objects.requireNonNull(user);
        this.resourceProductions = user.getResourceProduction().stream()
                .map(production -> production.convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        this.buildings = Collections.unmodifiableList(user.getBuildings());
        this.events = user.getEvents().stream().map(e -> convert(e, currentTick)).collect(Collectors.toList());
        this.id = user.getId();
        this.name = user.getName();
    }

    private static GameEventDTO convert(final GameEvent e, final Tick currentTick) {
        if (e instanceof ConstructionEvent) {
            return new ConstructionEventDTO(e.getCompletionTick().getDiff(currentTick, TimeUnit.SECONDS), ((ConstructionEvent) e).getConstructionInfo());
        }
        throw new IllegalStateException("Cannot convert GameEvent of type " + e.getClass().getSimpleName());
    }

    public int getId() {
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
