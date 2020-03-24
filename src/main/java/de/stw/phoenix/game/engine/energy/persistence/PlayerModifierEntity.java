package de.stw.phoenix.game.engine.energy.persistence;

import de.stw.phoenix.game.engine.energy.PlayerModifier;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="player_modifiers")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class PlayerModifierEntity implements PlayerModifier {
    @Id
    private long id;

    @Column(name="description", length=500)
    private String description;

    public PlayerModifierEntity() {

    }

    public PlayerModifierEntity(int id, String description) {
        this.id = id;
        this.description = Objects.requireNonNull(description);
    }
}
