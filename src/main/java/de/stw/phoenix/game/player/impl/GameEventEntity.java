package de.stw.phoenix.game.player.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name="player_events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="event_type", discriminatorType = DiscriminatorType.STRING)
public abstract class GameEventEntity implements GameEvent {
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="player_id", nullable = false)
    private Player player;

    @Embedded
    private Progress progress;

    @Embedded
    private Moment lastUpdate;

    public GameEventEntity() {

    }

    public GameEventEntity(Player player, Progress progress, Moment lastUpdate) {
        this.player = Objects.requireNonNull(player);
        this.progress = Objects.requireNonNull(progress);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
    }

    @Override
    public Moment getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isFinished() {
        return progress.isFinished();
    }

    public void updateProgress(double progressToAdd, TimeDuration duration, Moment lastUpdate) {
        Objects.requireNonNull(lastUpdate);
        Objects.requireNonNull(duration);
        this.progress.setValue(this.progress.getValue() + progressToAdd);
        this.progress.setDuration(duration);
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdated(Moment lastUpdate) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
    }
}
