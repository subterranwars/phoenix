package de.stw.core.resources;

import java.util.Objects;

public class Building {
    private final int id;
    private final String name;

    public Building(int id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
