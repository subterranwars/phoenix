package de.stw.phoenix.game.player.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.data.buildings.Building;
import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.MutableResourceStorage;
import de.stw.phoenix.game.engine.modules.construction.ConstructionEvent;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;

import java.util.*;
import java.util.stream.Collectors;

public class MutablePlayerImpl implements MutablePlayer {

    private final List<BuildingLevel> buildings;
    private final List<GameEvent> events;
    private final List<MutableResourceStorage> resources;
    private final long id;
    private final String name;

    MutablePlayerImpl(ImmutablePlayer delegate) {
        Objects.requireNonNull(delegate);
        this.id = delegate.getId();
        this.name = delegate.getName();
        this.buildings = Lists.newArrayList(delegate.getBuildings());
        this.events = Lists.newArrayList(delegate.getEvents());
        this.resources = delegate.getResources().stream().map(s -> new MutableResourceStorage(s.getResource(), s.getAmount(), s.getCapacity())).collect(Collectors.toList());
    }

    @Override
    public List<ImmutableResourceStorage> getResources() {
        final List<ImmutableResourceStorage> resources = this.resources.stream().map(MutableResourceStorage::asImmutable).collect(Collectors.toList());
        return Collections.unmodifiableList(resources);
    }

    @Override
    public ImmutableResourceStorage getStorage(Resource resource) {
        return getMutableStorage(resource).map(MutableResourceStorage::asImmutable).orElse(null);
    }

    @Override
    public List<BuildingLevel> getBuildings() {
        return Collections.unmodifiableList(this.buildings);
    }

    @Override
    public BuildingLevel getBuilding(Building building) {
        return asImmutable().getBuilding(building);
    }

    @Override
    public boolean canAfford(Map<Resource, Integer> costs) {
        return asImmutable().canAfford(costs);
    }

    @Override
    public List<GameEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    @Override
    public ConstructionEvent getConstructionEvent() {
        return asImmutable().getConstructionEvent();
    }

    @Override
    public List<GameEvent> getEvents(Tick tick) {
        return asImmutable().getEvents(tick);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setBuilding(BuildingLevel buildingLevel) {
        Optional<BuildingLevel> any = getBuildings().stream().filter(bl -> bl.getBuilding().getId() == buildingLevel.getBuilding().getId()).findAny();
        if (any.isPresent()) {
            buildings.remove(any.get());
        }
        buildings.add(buildingLevel);
    }

    @Override
    public void removeResources(Map<Resource, Integer> costs) {
        Objects.requireNonNull(costs);
        if (canAfford(costs)) {
            costs.entrySet().forEach(e -> getMutableStorage(e.getKey()).get().retrieve(e.getValue()));
        }
    }

    @Override
    public void addResources(Resource resource, double amount) {
        getMutableStorage(resource).ifPresent(storage -> storage.store(amount));
    }

    @Override
    public void addConstruction(ConstructionEvent constructionEvent) {
        Objects.requireNonNull(constructionEvent);
        this.events.add(constructionEvent);
    }

    @Override
    public void removeEvent(GameEvent event) {
        Objects.requireNonNull(event);
        events.remove(event);
    }

    @Override
    public void removeEvents(List<GameEvent> events) {
        Objects.requireNonNull(events);
        events.forEach(this::removeEvent);
    }

    @Override
    public ImmutablePlayer asImmutable() {
        return ImmutablePlayerImpl.builder(getId(), getName())
                .withBuildings(this.buildings)
                .withResources(this.resources.stream().map(MutableResourceStorage::asImmutable).collect(Collectors.toList()))
                .withEvents(this.events)
                .build();
    }

    private Optional<MutableResourceStorage> getMutableStorage(Resource resource) {
            Objects.requireNonNull(resource);
            return resources.stream()
                    .filter(r -> r.getResource().getId() == resource.getId())
                    .findAny();
    }
}