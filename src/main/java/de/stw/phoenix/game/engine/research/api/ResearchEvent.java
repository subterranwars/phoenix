package de.stw.phoenix.game.engine.research.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public class ResearchEvent implements GameEvent {
    @JsonIgnore
    private final PlayerRef playerRef;
    private final ResearchInfo researchInfo;
    private final Progress progress;
    private Moment lastUpdate;

    public ResearchEvent(PlayerRef playerRef, ResearchInfo researchInfo, double progress, TimeDuration estimatedDuration, Moment lastUpdate) {
        this.researchInfo = Objects.requireNonNull(researchInfo);
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

    public ResearchInfo getResearchInfo() {
        return researchInfo;
    }
}
