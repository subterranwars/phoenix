package de.stw.phoenix.game.engine.requirements;

import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.player.impl.Player;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("research")
public class ResearchRequirement extends RequirementEntity {

    @OneToOne
    @JoinColumn(name = "research_id")
    private Research research;

    @Column(name="level")
    private int level;

    private ResearchRequirement() {

    }

    protected ResearchRequirement(Research research, int level) {
        this.research = research;
        this.level = level;
    }

    @Override
    public boolean fulfills(Player player) {
        return player.getResearch(research).getLevel() >= level;
    }
}
