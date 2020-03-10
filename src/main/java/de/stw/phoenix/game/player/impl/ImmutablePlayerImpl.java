package de.stw.phoenix.game.player.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.api.GameEvent;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static de.stw.phoenix.game.engine.resources.api.Resources.DEFAULT_AMOUNT;
import static de.stw.phoenix.game.engine.resources.api.Resources.Food;
import static de.stw.phoenix.game.engine.resources.api.Resources.Iron;
import static de.stw.phoenix.game.engine.resources.api.Resources.MAX_STORAGE_CAPACITY;
import static de.stw.phoenix.game.engine.resources.api.Resources.Oil;
import static de.stw.phoenix.game.engine.resources.api.Resources.Stone;

// TODO MVR do we need this to be immutable?
public final class ImmutablePlayerImpl implements ImmutablePlayer {

    private final long id;
    private final String name;
    private final List<ImmutableResourceStorage> resources;
    private final List<BuildingLevel> buildings;
    private final List<GameEvent> events;
    private final List<ResourceSite> resourceSites;

    private ImmutablePlayerImpl(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.resources = Collections.unmodifiableList(builder.resources);
        this.buildings = Collections.unmodifiableList(builder.buildings);
        this.events = Collections.unmodifiableList(builder.events);
        this.resourceSites = Collections.unmodifiableList(builder.resourceSites);
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
    public List<ImmutableResourceStorage> getResources() {
		return resources;
	}

    @Override
    public List<ResourceSite> getResourceSites() {
        return resourceSites;
    }

    @Override
    public ImmutableResourceStorage getStorage(Resource resource) {
        Objects.requireNonNull(resource);
        return resources.stream().filter(r -> r.getResource().getId() == resource.getId()).findAny().orElse(null);
    }

    @Override
    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    @Override
    public BuildingLevel getBuilding(Building building) {
        Objects.requireNonNull(building);
        Optional<BuildingLevel> any = buildings.stream().filter(bl -> bl.getBuilding().getId() == building.getId()).findAny();
        return any.orElse(new BuildingLevel(building, 0));
    }

    @Override
    public boolean canAfford(Map<Resource, Integer> costs) {
        for (Map.Entry<Resource, Integer> entry : costs.entrySet()) {
            final Resource resource = entry.getKey();
            final int cost = entry.getValue();
            final ImmutableResourceStorage storage = getStorage(resource);
            if (storage == null || storage.getAmount() < cost) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<GameEvent> getEvents() {
        return events;
    }

    @Override
    public ConstructionEvent getConstructionEvent() {
        return (ConstructionEvent) getEvents().stream()
                .filter(e -> e.getClass().isAssignableFrom(ConstructionEvent.class))
                .findAny().orElse(null);
    }

    public static Builder builder(long id, String name) {
        return new Builder().id(id).name(name);
    }

    public static final class Builder {
        private long id;
        private String name;
        private List<ImmutableResourceStorage> resources = Lists.newArrayList();
        private List<BuildingLevel> buildings = Lists.newArrayList();
        private List<GameEvent> events = Lists.newArrayList();
        private List<ResourceSite> resourceSites = Lists.newArrayList();

        public Builder id(long id) {
            Preconditions.checkArgument(id > 0, "id must be > 0");
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder withResource(Resource resource, double amount, double storageCapacity) {
            this.resources.add(new ImmutableResourceStorage(resource, amount, storageCapacity));
            return this;
        }

        public Builder withResource(Resource resource, int amount) {
            return withResource(resource, amount, MAX_STORAGE_CAPACITY);
        }

        public Builder withResources(List<ImmutableResourceStorage> resourceStorages) {
            Objects.requireNonNull(resourceStorages);
            resourceStorages.forEach(res -> withResource(res.getResource(), res.getAmount(), res.getCapacity()));
            return this;
        }

        public Builder withBuilding(Building building, int level) {
            Objects.requireNonNull(building);
            Preconditions.checkArgument(level > 0);
            this.buildings.add(new BuildingLevel(building, level));
            return this;
        }

        public Builder withBuildings(List<BuildingLevel> buildings) {
            Objects.requireNonNull(buildings);
            buildings.forEach(b -> withBuilding(b.getBuilding(), b.getLevel()));
            return this;
        }

        public Builder withEvents(List<GameEvent> events) {
            this.events.addAll(events);
            return this;
        }

        public Builder withResourceSites(List<ResourceSite> sites) {
            this.resourceSites.addAll(sites);
            return this;
        }

        public Builder withDefaultResourceStorage() {
            withResource(Iron, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);
            withResource(Stone, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);;
            withResource(Oil, 0, MAX_STORAGE_CAPACITY);
            withResource(Food, 0, MAX_STORAGE_CAPACITY);
            return this;
        }

        public Builder withDefaultBuildings() {
            withBuilding(Buildings.Headquarter, 1);
            return this;
        }

        public Builder withDefaults() {
            withDefaultResourceStorage();
            withDefaultBuildings();
            return this;
        }

        public ImmutablePlayerImpl build() {
            return new ImmutablePlayerImpl(this);
        }


    }
}
