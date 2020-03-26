package de.stw.phoenix.game.engine.requirements;

import de.stw.phoenix.game.player.impl.Player;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("and")
public class AndRequirement extends RequirementEntity {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = RequirementEntity.class)
    @JoinColumn(name="parent_id")
    private List<Requirement> requirements;

    private AndRequirement() {

    }

    protected AndRequirement(List<Requirement> requirements) {
        this.requirements = Objects.requireNonNull(requirements);
    }

    @Override
    public boolean fulfills(Player player) {
        long fulfillingRequirements = requirements.stream().filter(r -> r.fulfills(player)).count();
        return fulfillingRequirements == requirements.size();
    }
}
