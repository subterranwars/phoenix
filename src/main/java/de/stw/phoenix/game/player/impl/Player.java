package de.stw.phoenix.game.player.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.energy.PlayerModifier;
import de.stw.phoenix.game.engine.energy.persistence.PlayerModifierEntity;
import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.player.api.ResourceSite;
import de.stw.phoenix.game.player.api.ResourceStorage;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

@Entity
@Table(name="players")
public class Player {

    @Id
    @Column(name="id")
    private long id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name="totalDroneCount")
    private long totalDroneCount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="player_id", nullable = false)
    private List<BuildingLevel> buildings = Lists.newArrayList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="player_id", nullable = false)
    private List<ResearchLevel> researchs = Lists.newArrayList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = GameEventEntity.class, mappedBy = "playerRef")
    private List<GameEvent> events = Lists.newArrayList();

    @ElementCollection
    @CollectionTable(name="player_resources", joinColumns=@JoinColumn(name="player_id"))
    private List<ResourceStorage> resources = Lists.newArrayList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="player_id")
    private List<ResourceSite> resourceSites = Lists.newArrayList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = PlayerModifierEntity.class)
    @JoinColumn(name="player_id", nullable = false)
    private List<PlayerModifier> modifiers = Lists.newArrayList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="player_id")
    private List<Notification> notifications = Lists.newArrayList();

    private Player() {

    }

    private Player(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.buildings = builder.buildings;
        this.researchs = builder.researchs;
        this.events = builder.events;
        this.resources = builder.resources;
        this.resourceSites = builder.resourceSites;
        this.modifiers = builder.modifiers;
        this.notifications = builder.notifications;
        this.totalDroneCount = builder.totalDroneCount;
    }

    public List<ResourceStorage> getResources() {
        return ImmutableList.copyOf(resources);
    }

    public List<ResourceSite> getResourceSites() {
        return ImmutableList.copyOf(resourceSites);
    }

    public ResourceStorage getStorage(Resource resource) {
        return findStorage(resource).orElse(null);
    }

    public List<BuildingLevel> getBuildings() {
        return ImmutableList.copyOf(this.buildings);
    }

    public BuildingLevel getBuilding(Building building) {
        Objects.requireNonNull(building);
        Optional<BuildingLevel> any = buildings.stream().filter(bl -> bl.getBuilding().getId() == building.getId()).findAny();
        return any.orElse(new BuildingLevel(building, 0));
    }

    public List<ResearchLevel> getResearchs() {
        return ImmutableList.copyOf(this.researchs);
    }

    // TODO MVR identical to getBuilding(Building)
    public ResearchLevel getResearch(Research research) {
        Objects.requireNonNull(research);
        Optional<ResearchLevel> any = researchs.stream().filter(rl -> rl.getResearch().getId() == research.getId()).findAny();
        return any.orElse(new ResearchLevel(research, 0));
    }

    public boolean canAfford(Map<Resource, Double> costs) {
        for (Map.Entry<Resource, Double> entry : costs.entrySet()) {
            final Resource resource = entry.getKey();
            final double cost = entry.getValue();
            final ResourceStorage storage = getStorage(resource);
            if (storage == null || storage.getAmount() < cost) {
                return false;
            }
        }
        return true;
    }

    public List<GameEvent> getEvents() {
        return ImmutableList.copyOf(this.events);
    }

    public <T extends GameEvent> List<T> findEvents(Class<T> eventType) {
        Objects.requireNonNull(eventType);
        return getEvents().stream()
                .filter(e -> eventType.isAssignableFrom(e.getClass()))
                .map( e -> (T) e)
                .collect(Collectors.toList());
    }

    public <T extends GameEvent> Optional<T> findSingleEvent(Class<T> eventType) {
        Objects.requireNonNull(eventType);
        final List<T> events = findEvents(eventType);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        // TODO MVR add logging if more than 1 elements
        return Optional.of(events.get(0));
    }

    public List<PlayerModifier> getModifiers() {
        return ImmutableList.copyOf(modifiers);
    }

    public <T extends PlayerModifier> List<T> findModifier(Class<T> modifierType) {
        Objects.requireNonNull(modifierType);
        return modifiers.stream()
                .filter(e -> modifierType.isAssignableFrom(e.getClass()))
                .map(e -> (T) e)
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTotalDroneCount() {
        return totalDroneCount;
    }

    public void updateTotalDroneCount(long totalDroneCount) {
        this.totalDroneCount = totalDroneCount;
    }

    public void setBuilding(BuildingLevel buildingLevel) {
        Optional<BuildingLevel> any = getBuildings().stream().filter(bl -> bl.getBuilding().getId() == buildingLevel.getBuilding().getId()).findAny();
        if (any.isPresent()) {
            buildings.remove(any.get());
        }
        buildings.add(buildingLevel);
    }

    public void setResearch(ResearchLevel newLevel) {
        Optional<ResearchLevel> any = getResearchs().stream().filter(rl -> rl.getResearch().getId() == newLevel.getResearch().getId()).findAny();
        if (any.isPresent()) {
            researchs.remove(any.get());
        }
        researchs.add(newLevel);
    }

    public void removeResources(Map<Resource, Double> costs) {
        Objects.requireNonNull(costs);
        if (canAfford(costs)) {
            costs.entrySet().forEach(e -> findStorage(e.getKey()).get().retrieve(e.getValue()));
        }
    }

    public void addModifier(PlayerModifier modifier) {
        Objects.requireNonNull(modifier);
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier);
        }
    }

    public void removeModifier(PlayerModifier modifier) {
        Objects.requireNonNull(modifier);
        modifiers.remove(modifier);
    }

    public void removeResources(Resource resource, double consumption) {
        findStorage(resource).ifPresent(storage -> storage.retrieve(consumption));
    }

    public void addResources(Resource resource, double amount) {
        findStorage(resource).ifPresent(storage -> storage.store(amount));
    }

    public void addResourceSite(ResourceSite resourceSite) {
        this.resourceSites.add(resourceSite);
    }

    public void removeResourceSite(ResourceSite resourceSite) {
        this.resourceSites.remove(resourceSite);
    }

    public Optional<ResourceSite> getResourceSite(long resourceSiteId) {
        return this.resourceSites.stream().filter(site -> Objects.equals(site.getId(), resourceSiteId)).findAny();
    }

    public void updateResourceStorage(long maxStorage) {
        if (getResources().get(0).getCapacity() != maxStorage) {
            final List<ResourceStorage> newStorages = this.resources.stream()
                    .map(storage -> new ResourceStorage(storage.getResource(), storage.getAmount(), maxStorage))
                    .collect(Collectors.toList());
            resources.clear();
            resources.addAll(newStorages);
        }
    }

    public <T extends GameEvent> void addEvent(T event) {
        Objects.requireNonNull(event);
        this.events.add(event);
    }

    public void removeEvent(GameEvent event) {
        Objects.requireNonNull(event);
        events.remove(event);
    }

    public void removeEvents(List<GameEvent> events) {
        Objects.requireNonNull(events);
        events.forEach(this::removeEvent);
    }

    public void addEvents(List<GameEvent> newEvents) {
        Objects.requireNonNull(newEvents);
        newEvents.forEach(this::addEvent);
    }

    private Optional<ResourceStorage> findStorage(Resource resource) {
            Objects.requireNonNull(resource);
            return resources.stream()
                    .filter(r -> r.getResource().getId() == resource.getId())
                    .findAny();
    }

    public void addNotification(Notification notification) {
	    Objects.requireNonNull(notification);
	    notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return ImmutableList.copyOf(notifications);
    }

    public void removeNotificationById(long notificationId) {
	    notifications.stream().filter(n -> n.getId() == notificationId).findAny().ifPresent(n -> notifications.remove(n));
    }

    public void markNotificationAsRead(long notificationId) {
        notifications.stream().filter(n -> n.getId() == notificationId).findAny().ifPresent(n -> {
            final Notification readNotification = new Notification(n.getCreationDate(), n.getLabel(), n.getContent(), true);
            notifications.remove(n);
            notifications.add(readNotification);
        });
    }

    public PlayerRef asPlayerRef() {
        return new PlayerRef(this.id);
    }

    public static Builder builder(long id, String name) {
        return new Builder().id(id).name(name);
    }

    public static final class Builder {
        private long id;
        private String name;
        private List<ResourceStorage> resources = Lists.newArrayList();
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
            this.resources.add(new ResourceStorage(resource, amount, storageCapacity));
            return this;
        }

        public Builder withResource(Resource resource, int amount) {
            return withResource(resource, amount, MAX_STORAGE_CAPACITY);
        }

        public Builder withResources(List<ResourceStorage> resourceStorages) {
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

        public Player build() {
            return new Player(this);
        }
    }
}