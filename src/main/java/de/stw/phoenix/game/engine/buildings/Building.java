package de.stw.phoenix.game.engine.buildings;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

// TODO MVR maybe rename to BuildingData and the Building itself is BuildingData + Level?
@Table(name="buildings")
@Entity
public final class Building implements BuildingRef {

    @Id
    private int id;
    private String name;
    private String label;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name="building_costs", joinColumns=@JoinColumn(name="building_id"))
    @MapKeyJoinColumn(name="resource_id")
    @Column(name="costs")
    private Map<Resource, Double> costs;

    private TimeDuration buildTime;
    private int energyConsumption;

    private Building() {

    }

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

    public Map<Resource, Double> getCosts() {
        return costs;
    }

    public TimeDuration getBuildTime() {
        return buildTime;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
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
        private int energyConsumption;
        private Map<Resource, Double> costs = Maps.newHashMap();

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

        public Builder costsResource(Resource resource, double amount) {
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
