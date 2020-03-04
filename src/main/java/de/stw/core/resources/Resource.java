package de.stw.core.resources;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 *  Represents a single in-game resource e.g iron or stone 
 * 
 * @author mvr, cjs
 */
public class Resource {
    private int id;

    @JsonValue
    private String name;

    public Resource(int id, String name) {
        Preconditions.checkArgument(id > 0);
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