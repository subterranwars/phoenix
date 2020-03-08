package de.stw.phoenix.game.engine.modules.construction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final PlayerRef playerRef;
    private final ConstructionInfo constructionInfo;
    private final Moment completionMoment;

    public ConstructionEvent(PlayerRef playerRef, ConstructionInfo constructionInfo, Moment completionMoment) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionMoment = Objects.requireNonNull(completionMoment);
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
}
