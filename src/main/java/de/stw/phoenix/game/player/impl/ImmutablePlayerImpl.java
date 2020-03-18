package de.stw.phoenix.game.player.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.energy.PlayerModifier;
import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.ResearchLevel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final List<ResearchLevel> researchs;
    private final List<GameEvent> events;
    private final List<ResourceSite> resourceSites;
    private final List<PlayerModifier> modifiers;
    private final long totalDroneCount;
    private final List<Notification> notifications;

    private ImmutablePlayerImpl(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.resources = ImmutableList.copyOf(builder.resources);
        this.buildings = ImmutableList.copyOf(builder.buildings);
        this.events = ImmutableList.copyOf(builder.events);
        this.resourceSites = ImmutableList.copyOf(builder.resourceSites);
        this.modifiers = ImmutableList.copyOf(builder.modifiers);
        this.researchs = ImmutableList.copyOf(builder.researchs);
        this.totalDroneCount = builder.totalDroneCount;
        this.notifications = ImmutableList.copyOf(builder.notifications);
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
    public List<ResearchLevel> getResearchs() {
        return researchs;
    }

    // TODO MVR identical to getBuilding(Building)
    @Override
    public ResearchLevel getResearch(Research research) {
        Objects.requireNonNull(research);
        Optional<ResearchLevel> any = researchs.stream().filter(rl -> rl.getResearch().getId() == research.getId()).findAny();
        return any.orElse(new ResearchLevel(research, 0));
    }

    @Override
    public boolean canAfford(Map<Resource, Double> costs) {
        for (Map.Entry<Resource, Double> entry : costs.entrySet()) {
            final Resource resource = entry.getKey();
            final double cost = entry.getValue();
            final ImmutableResourceStorage storage = getStorage(resource);
            if (storage == null || storage.getAmount() < cost) {
                return false;
            }
        }
        return true;
    }

    @Override
    public long getTotalDroneCount() {
        return totalDroneCount;
    }

    @Override
    public List<GameEvent> getEvents() {
        return events;
    }

    @Override
    public <T extends GameEvent> Optional<T> findSingleEvent(Class<T> eventType) {
        Objects.requireNonNull(eventType);
        final List<T> events = findEvents(eventType);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        // TODO MVR add logging if more than 1 elements
        return Optional.of(events.get(0));
    }

    @Override
    public <T extends GameEvent> List<T> findEvents(Class<T> eventType) {
        Objects.requireNonNull(eventType);
        return getEvents().stream()
                .filter(e -> eventType.isAssignableFrom(e.getClass()))
                .map( e -> (T) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public <T extends PlayerModifier> List<T> findModifier(Class<T> modifierType) {
        Objects.requireNonNull(modifierType);
        return modifiers.stream()
                .filter(e -> modifierType.isAssignableFrom(e.getClass()))
                .map(e -> (T) e)
                .collect(Collectors.toList());
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
        private List<PlayerModifier> modifiers = Lists.newArrayList();
        private List<ResearchLevel> researchs = Lists.newArrayList();
        private long totalDroneCount = 0;
        private List<Notification> notifications = Lists.newArrayList();

        public Builder id(long id) {
            Preconditions.checkArgument(id > 0, "id must be > 0");
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        // TODO MVR ensure the same resource is not added twice
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

        // TODO MVR ensure the same building is not added twice
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

        // TODO MVR ensure the same research is not added twice
        public Builder withResearch(Research research, int level) {
            Objects.requireNonNull(research);
            Preconditions.checkArgument(level > 0);
            this.researchs.add(new ResearchLevel(research, level));
            return this;
        }

        public Builder withResearchs(List<ResearchLevel> researchs) {
            Objects.requireNonNull(researchs);
            researchs.forEach(r -> withResearch(r.getResearch(), r.getLevel()));
            return this;
        }

        public Builder withEvents(List<GameEvent> events) {
            this.events.addAll(events);
            return this;
        }

        public Builder withModifiers(List<PlayerModifier> modifiers) {
            Objects.requireNonNull(modifiers);
            modifiers.forEach(this::withModifier);
            return this;
        }

        public Builder withModifier(PlayerModifier modifier) {
            Objects.requireNonNull(modifier);
            if (!this.modifiers.contains(modifier)) {
                this.modifiers.add(modifier);
            }
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

        public Builder withTotalDroneCount(long totalDroneCount) {
            Preconditions.checkArgument(totalDroneCount >= 0);
            this.totalDroneCount = totalDroneCount;
            return this;
        }
        
        public Builder withNotifications(List<Notification> notifications) {
            this.notifications.addAll(notifications);
            return this;
        }

        public ImmutablePlayerImpl build() {
            return new ImmutablePlayerImpl(this);
        }


    }

    @Override
    public List<Notification> getNotifications() {
	return notifications;
    }
}
