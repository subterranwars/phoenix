package de.stw.core.user;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import de.stw.core.buildings.*;
import de.stw.core.resources.Resource;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;

import java.util.*;
import java.util.stream.Collectors;

import static de.stw.core.resources.Resources.*;

public final class User {
    private final int id;
    private final String name;
    private final String password; // TODO MVR use passwordhash
    private final List<ResourceStorage> resources;
    private final List<BuildingLevel> buildings;
    private final List<GameEvent> events = Lists.newArrayList();

    private User(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.password = builder.password;
        this.resources = Collections.unmodifiableList(builder.resources);
        this.buildings = Collections.unmodifiableList(builder.buildings);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // TODO MVR this is very unsecure... do not store passwords or hashs in memory :D
    public String getPassword() {
        return password;
    }

    public List<ResourceStorage> getResources() {
		return resources;
	}

	public List<ResourceProduction> getResourceProduction() {
        return getResources().stream()
        .map(storage -> new ResourceProduction(storage, 60))
        .collect(Collectors.toList());
    }

    public ResourceStorage getStorage(Resource resource) {
        Objects.requireNonNull(resource);
        return resources.stream().filter(r -> r.getResource().getId() == resource.getId()).findAny().orElse(null);
    }

    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    public BuildingLevel getBuilding(Building building) {
        Objects.requireNonNull(building);
        Optional<BuildingLevel> any = buildings.stream().filter(bl -> bl.getBuilding().getId() == building.getId()).findAny();
        return any.orElse(new BuildingLevel(building, 0));
    }

    public boolean canAfford(Map<Resource, Integer> costs) {
        for (Map.Entry<Resource, Integer> entry : costs.entrySet()) {
            final Resource resource = entry.getKey();
            final int cost = entry.getValue();
            final ResourceStorage storage = getStorage(resource);
            if (storage == null || storage.getAmount() < cost) {
                return false;
            }
        }
        return true;
    }

    public void removeResources(Map<Resource, Integer> costs) {
        Objects.requireNonNull(costs);
        if (canAfford(costs)) {
            costs.entrySet().forEach(e -> getStorage(e.getKey()).retrieve(e.getValue()));
        }
    }

    public void addConstruction(ConstructionEvent constructionEvent) {
        Objects.requireNonNull(constructionEvent);
        this.events.add(constructionEvent);
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public static Builder builder(int id, String name) {
        return new Builder().id(id).name(name);
    }

    public ConstructionEvent getConstructionEvent() {
        return (ConstructionEvent) getEvents().stream()
                .filter(e -> e.getClass().isAssignableFrom(ConstructionEvent.class))
                .findAny().orElse(null);
    }

    public static final class Builder {
        public String password;
        private int id;
        private String name;
        private List<ResourceStorage> resources = Lists.newArrayList();
        private List<BuildingLevel> buildings = Lists.newArrayList();

        public Builder id(int id) {
            Preconditions.checkArgument(id > 0, "id must be > 0");
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder password(String password) {
            this.password = Objects.requireNonNull(password);
            return this;
        }

        public Builder withResource(Resource resource, int amount, int storageCapacity) {
            this.resources.add(new ResourceStorage(resource, amount, storageCapacity));
            return this;
        }

        public Builder withResource(Resource resource, int amount) {
            return withResource(resource, amount, MAX_STORAGE_CAPACITY);
        }

        public Builder withBuilding(Building building, int level) {
            Objects.requireNonNull(building);
            Preconditions.checkArgument(level > 0);
            this.buildings.add(new BuildingLevel(building, level));
            return this;
        }

        public Builder withDefaultResourceStorage() {
            withResource(Iron, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);
            withResource(Stone, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);
            withResource(Food, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);
            withResource(Oil, DEFAULT_AMOUNT, MAX_STORAGE_CAPACITY);
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

        public User build() {
            return new User(this);
        }


    }
}
