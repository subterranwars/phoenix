package de.stw.core;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {
    private final int id;
    private final String name;
    private final List<ResourceCount> resources;

    public Player(int id, String name, List<ResourceCount> resources) {
        Preconditions.checkArgument(id > 0, "id must be > 0");
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.resources = Collections.unmodifiableList(Objects.requireNonNull(resources));
    }

    public int getId() {
        return id;
    }

    public List<ResourceCount> getResources() {
        return resources;
    }

    public String getName() {
        return name;
    }
}
