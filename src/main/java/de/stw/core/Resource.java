package de.stw.core;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class Resource {
    private int id;
    private String name;
    private float ratePerHour;

    public Resource(int id, String name, float ratePerHour) {
        Preconditions.checkArgument(id > 0);
        Preconditions.checkArgument(ratePerHour > 0);
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.ratePerHour = ratePerHour;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getRatePerHour() {
        return ratePerHour;
    }
}
