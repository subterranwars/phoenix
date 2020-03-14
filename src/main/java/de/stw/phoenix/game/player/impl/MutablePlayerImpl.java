package de.stw.phoenix.game.player.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.energy.PlayerModifier;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.MutableResourceStorage;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MutablePlayerImpl implements MutablePlayer {

    private final List<BuildingLevel> buildings;
    private final List<GameEvent> events;
    private final List<MutableResourceStorage> resources;
    private List<MutableResourceSite> resourceSites;
    private final long id;
    private final String name;
    private List<PlayerModifier> modifiers;

    MutablePlayerImpl(ImmutablePlayer delegate) {
        Objects.requireNonNull(delegate);
        this.id = delegate.getId();
        this.name = delegate.getName();
        this.buildings = Lists.newArrayList(delegate.getBuildings());
        this.events = Lists.newArrayList(delegate.getEvents());
        this.resources = delegate.getResources().stream().map(s -> new MutableResourceStorage(s.getResource(), s.getAmount(), s.getCapacity())).collect(Collectors.toList());
        this.resourceSites = delegate.getResourceSites().stream().map(MutableResourceSite::new).collect(Collectors.toList());
        this.modifiers = Lists.newArrayList(delegate.getModifiers());
    }

    @Override
    public List<ImmutableResourceStorage> getResources() {
        final List<ImmutableResourceStorage> resources = this.resources.stream().map(MutableResourceStorage::asImmutable).collect(Collectors.toList());
        return Collections.unmodifiableList(resources);
    }

    @Override
    public List<ResourceSite> getResourceSites() {
        List<ResourceSite> sites = resourceSites.stream().map(MutableResourceSite::asImmutable).collect(Collectors.toList());
        return Collections.unmodifiableList(sites);
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
        // TODO MVR Collections.unmodifiableList does not create a copy, but ensures it is unmodifiable
        // This is bad, as we want to iterate over the getEvents() and invoke removeEvent(..) later
        // Ensure that ALL Collections.unmodifiableList(...) calls in this application are actually implemented properly
        // Either create a copy before using unmodifiableList or use ImmutableLists from guava (or similar)
        return Collections.unmodifiableList(Lists.newArrayList(this.events));
    }

    @Override
    public <T extends GameEvent> List<T> findEvents(Class<T> eventType) {
        return asImmutable().findEvents(eventType);
    }

    @Override
    public <T extends GameEvent> Optional<T> findSingleEvent(Class<T> eventType) {
        return asImmutable().findSingleEvent(eventType);
    }

    @Override
    public List<PlayerModifier> getModifiers() {
        return asImmutable().getModifiers();
    }

    @Override
    public <T extends PlayerModifier> List<T> findModifier(Class<T> modifierType) {
        return asImmutable().findModifier(modifierType);
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
    public void addModifier(PlayerModifier modifier) {
        Objects.requireNonNull(modifier);
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier);
        }
    }

    @Override
    public void removeModifier(PlayerModifier modifier) {
        Objects.requireNonNull(modifier);
        modifiers.remove(modifier);
    }

    @Override
    public void removeResources(Resource resource, double consumption) {
        getMutableStorage(resource).ifPresent(storage -> storage.retrieve(consumption));
    }

    @Override
    public void addResources(Resource resource, double amount) {
        getMutableStorage(resource).ifPresent(storage -> storage.store(amount));
    }

    @Override
    public void addResourceSite(ResourceSite resourceSite) {
        this.resourceSites.add(new MutableResourceSite(resourceSite));
    }

    @Override
    public void removeResourceSite(MutableResourceSite resourceSite) {
        this.resourceSites.remove(resourceSite);
    }

    @Override
    public Optional<MutableResourceSite> getResourceSite(long resourceSiteId) {
        return this.resourceSites.stream().filter(site -> Objects.equals(site.getId(), resourceSiteId)).findAny();
    }

    @Override
    public void updateResourceStorage(long maxStorage) {
        if (getResources().get(0).getCapacity() != maxStorage) {
            final List<MutableResourceStorage> newStorages = this.resources.stream()
                    .map(storage -> new MutableResourceStorage(storage.getResource(), storage.getAmount(), maxStorage))
                    .collect(Collectors.toList());
            resources.clear();
            resources.addAll(newStorages);
        }
    }

    @Override
    public <T extends GameEvent> void addEvent(T event) {
        Objects.requireNonNull(event);
        this.events.add(event);
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
    public void addEvents(List<GameEvent> newEvents) {
        Objects.requireNonNull(newEvents);
        newEvents.forEach(this::addEvent);
    }

    @Override
    public ImmutablePlayer asImmutable() {
        return ImmutablePlayerImpl.builder(getId(), getName())
                .withBuildings(this.buildings)
                .withResources(this.resources.stream().map(MutableResourceStorage::asImmutable).collect(Collectors.toList()))
                .withResourceSites(this.resourceSites.stream().map(MutableResourceSite::asImmutable).collect(Collectors.toList()))
                .withEvents(this.events)
                .withModifiers(this.modifiers)
                .build();
    }

    private Optional<MutableResourceStorage> getMutableStorage(Resource resource) {
            Objects.requireNonNull(resource);
            return resources.stream()
                    .filter(r -> r.getResource().getId() == resource.getId())
                    .findAny();
    }
}
