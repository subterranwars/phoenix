package de.stw.phoenix.game.engine.construction.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final PlayerRef playerRef;
    private final ConstructionInfo constructionInfo;
    private final Progress progress;
    private Moment lastUpdate;

    public ConstructionEvent(PlayerRef playerRef, ConstructionInfo constructionInfo, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.playerRef = Objects.requireNonNull(playerRef);
        this.progress = Progress.builder()
                .withValue(progress)
                .withDuration(estimatedDuration).build();
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
    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    @Override
    public boolean isFinished() {
        return progress.isFinished();
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }
}
