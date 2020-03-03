package de.stw.core.buildings;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import de.stw.core.resources.Resource;
import de.stw.core.resources.Resources;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Building {
    private final int id;
    private final String name;
    private final Map<Resource, Integer> costs;

    private Building(final Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.costs = Collections.unmodifiableMap(builder.costs);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Resource, Integer> getCosts() {
        return costs;
    }

    public Map<Resource, Integer> calculateCosts(int level) {
        Preconditions.checkArgument(level > 0);
        if (level == 1) {
            return getCosts();
        }
        int actualLevel = level - 1;
        final Map<Resource, Integer> calculatedCosts = getCosts().entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> {
            int baseCost = e.getValue();
            double costs = baseCost * Math.pow(2, Math.pow(actualLevel, 0.5));
            // Make values a bit nicer
            return Double.valueOf(costs - (costs % 10)).intValue();
        }));
        return calculatedCosts;
    }

    public static Builder builder(int id, String name) {
        return new Builder().id(id).name(name);
    }

    public static final class Builder {

        private String name;
        private int id;
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

        public Building build() {
            return new Building(this);
        }
    }
}
