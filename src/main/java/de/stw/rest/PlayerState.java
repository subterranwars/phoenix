package de.stw.rest;

import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.GameEvent;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerState {
    private int id;
    private String name;
    private List<ResourceProduction> resourceProductions;
    private List<BuildingLevel> buildings;
    private List<GameEvent> events;

    public PlayerState(User user) {
        Objects.requireNonNull(user);
        this.resourceProductions = user.getResourceProduction().stream()
                .map(production -> production.convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        this.buildings = Collections.unmodifiableList(user.getBuildings());
        this.events = Collections.unmodifiableList(user.getEvents());
        this.id = user.getId();
        this.name = user.getName();
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
}
