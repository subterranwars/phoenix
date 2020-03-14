package de.stw.phoenix.game.engine.buildings;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

// TODO MVR maybe rename to BuildingData and the Building itself is BuildingData + Level?
public final class Building implements BuildingRef {
    private final int id;
    private final String name;
    private final String label;
    private final String description;
    private final Map<Resource, Integer> costs;
    private final TimeDuration buildTime;
    private final int energyConsumption;

    private Building(final Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.label = builder.label;
        this.description = builder.description;
        this.buildTime = TimeDuration.ofSeconds(builder.buildTime);
        this.costs = ImmutableMap.copyOf(builder.costs);
        this.energyConsumption = builder.energyConsumption;
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

    public TimeDuration getBuildTime() {
        return buildTime;
    }

    public static Builder builder(int id, String name) {
        return new Builder().id(id).name(name);
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public static final class Builder {

        private String name;
        private String label;
        private String description;
        private int id;
        private long buildTime;
        private int energyConsumption;
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
            Preconditions.checkArgument(consumption >= 0);
            this.energyConsumption = consumption;
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
