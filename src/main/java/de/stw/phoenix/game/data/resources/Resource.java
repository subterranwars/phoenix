package de.stw.phoenix.game.data.resources;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 *  Represents a single in-game resource e.g iron or stone 
 * 
 * @author mvr, cjs
 */
public class Resource {
    private final int id;

    @JsonValue
    private final String name;

    private final float occurrence;

    private Resource(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.occurrence = builder.occurrence;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getOccurrence() {
        return occurrence;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String name;
        private float occurrence;

        public Builder id(int id) {
            Preconditions.checkArgument(id > 0);
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder occurrence(float occurrence) {
            Preconditions.checkArgument(occurrence > 0 && occurrence <= 1, "occurrence must be > 0 and <= 1");
            this.occurrence = occurrence;
            return this;
        }

        public Resource build() {
            return new Resource(this);
        }
    }
}