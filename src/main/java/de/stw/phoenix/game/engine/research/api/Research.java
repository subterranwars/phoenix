package de.stw.phoenix.game.engine.research.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public final class Research implements ResearchRef {
    private final int id;
    private final String label;
    private final String description;
    private final TimeDuration researchTime;

    private Research(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.label = builder.label;
        this.description = builder.description;
        this.researchTime = builder.researchTime;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public TimeDuration getResearchTime() {
        return researchTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String label;
        private String description;
        private TimeDuration researchTime;

        public Builder id(int id) {
            Preconditions.checkArgument(id > 0);
            this.id = id;
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder description(String description) {
            this.description = Objects.requireNonNull(description);
            return this;
        }

        public Builder researchTime(TimeDuration duration) {
            this.researchTime = Objects.requireNonNull(duration);
            return this;
        }

        public Research build() {
            return new Research(this);
        }
    }
}
