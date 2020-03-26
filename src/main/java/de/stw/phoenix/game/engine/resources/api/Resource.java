package de.stw.phoenix.game.engine.resources.api;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 *  Represents a single in-game resource e.g iron or stone 
 * 
 * @author mvr, cjs
 */
@Table(name="resources")
@Entity
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    @JsonValue
    private String name;

    private float occurrence;

    // Required for Hibernate
    public Resource() {

    }

    private Resource(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.occurrence = builder.occurrence;
    }

    public long getId() {
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
        private long id;
        private String name;
        private float occurrence;

        public Builder id(long id) {
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