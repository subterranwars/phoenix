package de.stw.core.user;

import com.google.common.base.Preconditions;

import com.google.common.collect.Lists;
import de.stw.core.buildings.Building;
import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.Buildings;
import de.stw.core.resources.Resource;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.stw.core.resources.Resources.*;
import static de.stw.core.resources.Resources.MAX_STORAGE_CAPACITY;

public class User {
    private final int id;
    private final String name;
    private final List<ResourceStorage> resources;
    private final List<BuildingLevel> buildings;

    private User(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.resources = Collections.unmodifiableList(builder.resources);
        this.buildings = Collections.unmodifiableList(builder.buildings);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ResourceStorage> getResources() {
		return resources;
	}

	public List<ResourceProduction> getResourceProduction() {
        return getResources().stream()
        .map(storage -> new ResourceProduction(storage, 60))
        .collect(Collectors.toList());
    }

    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    public static Builder builder(int id, String name) {
        return new Builder().id(id).name(name);
    }

    public static class Builder {
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
            withResource(Iron, 0, MAX_STORAGE_CAPACITY);
            withResource(Stone, 0, MAX_STORAGE_CAPACITY);
            withResource(Food, 0, MAX_STORAGE_CAPACITY);
            withResource(Oil, 0, MAX_STORAGE_CAPACITY);
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
