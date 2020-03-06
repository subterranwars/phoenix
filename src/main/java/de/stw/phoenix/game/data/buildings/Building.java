package de.stw.phoenix.game.data.buildings;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.data.resources.Resources;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class Building implements BuildingRef {
    private final int id;
    private final String name;
    private final String label;
    private final String description;
    private final Map<Resource, Integer> costs;
    private final long buildTimeInSeconds;

    private Building(final Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.label = builder.label;
        this.description = builder.description;
        this.buildTimeInSeconds = builder.buildTime;
        this.costs = Collections.unmodifiableMap(builder.costs);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public Map<Resource, Integer> getCosts() {
        return costs;
    }

    public long getBuildTimeInSeconds() {
        return buildTimeInSeconds;
    }

    public static Builder builder(int id, String name) {
        return new Builder().id(id).name(name);
    }

    public static final class Builder {

        private String name;
        private String label;
        private String description;
        private int id;
        private long buildTime;
        private Map<Resource, Integer> costs = Maps.newHashMap();

        public Builder id(int id) {
            Preconditions.checkArgument(id > 0);
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        public Builder costsResource(Resource resource, int amount) {
            Objects.requireNonNull(resource);
            Preconditions.checkArgument(amount > 0);
            this.costs.put(resource, amount);
            return this;
        }

        public Builder costsIron(int amount) {
            return costsResource(Resources.Iron, amount);
        }

        public Builder costsStone(int amount) {
            return costsResource(Resources.Stone, amount);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder description(String description) {
            this.description = Objects.requireNonNull(description);
            return this;
        }

        public Builder energyConsumption(int consumption) {
            // TODO MVR not implemented yet
            return this;
        }

        public Builder buildTime(long duration, TimeUnit timeUnit) {
            this.buildTime = TimeUnit.SECONDS.convert(duration, timeUnit);
            return this;
        }

        public Building build() {
            return new Building(this);
        }
    }
}
