package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.player.impl.GameEventEntity;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@DiscriminatorValue("construction")
public class ConstructionEvent extends GameEventEntity {

    // TODO MVR use BuildingRef where possible?
    // What to build
    @OneToOne
    @JoinColumn(name="building_id")
    private Building building;

    // which level to build
    @Column(name="level")
    private int levelToBuild;

    private ConstructionEvent() {

    }

    public ConstructionEvent(Player player, Building building, int levelToBuild, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        super(player, Progress.builder()
                .withValue(progress)
                .withDuration(estimatedDuration).build(), lastUpdate);
        this.building = Objects.requireNonNull(building);
        this.levelToBuild = levelToBuild;
    }

    public int getLevelToBuild() {
        return levelToBuild;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
