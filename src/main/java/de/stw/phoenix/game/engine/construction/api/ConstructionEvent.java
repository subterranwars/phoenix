package de.stw.phoenix.game.engine.construction.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.engine.api.events.EventVisitor;
import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.time.Moment;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final PlayerRef playerRef;
    private final ConstructionInfo constructionInfo;
    private final Moment completionMoment;

    public ConstructionEvent(PlayerRef playerRef, ConstructionInfo constructionInfo, Moment completionMoment) {
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionMoment = Objects.requireNonNull(completionMoment);
        this.playerRef = Objects.requireNonNull(playerRef);
    }

    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }

    @Override
    public Moment getCompletionMoment() {
        return completionMoment;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
