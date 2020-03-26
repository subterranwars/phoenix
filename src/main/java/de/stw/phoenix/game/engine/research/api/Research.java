package de.stw.phoenix.game.engine.research.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import de.stw.phoenix.game.engine.requirements.Requirement;
import de.stw.phoenix.game.engine.requirements.RequirementEntity;
import de.stw.phoenix.game.engine.requirements.Requirements;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Table(name="researchs")
@Entity
public final class Research implements ResearchRef {

    @Id
    private int id;

    private String label;

    @Column(length = 1000)
    private String description;

    private TimeDuration researchTime;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = RequirementEntity.class, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="requirement_id")
    @JsonIgnore
    private Requirement requirement;

    public Research() {

    }

    private Research(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.label = builder.label;
        this.description = builder.description;
        this.researchTime = builder.researchTime;
        this.requirement = builder.requirement;
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

    public Requirement getRequirement() {
        if (requirement == null) {
            return player -> true;
        }
        return requirement;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String label;
        private String description;
        private TimeDuration researchTime;
        private Requirement requirement;

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

        public Builder requirements(Requirement...requirements) {
            this.requirement = Requirements.and(requirements);
            return this;
        }

        public Research build() {
            return new Research(this);
        }
    }
}
