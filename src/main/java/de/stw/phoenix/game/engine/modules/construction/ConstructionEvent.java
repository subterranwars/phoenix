package de.stw.phoenix.game.engine.modules.construction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;

// TODO MVR how to serialize?
public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final PlayerRef playerRef;
    private final ConstructionInfo constructionInfo;
    private final Tick completionTick;

    public ConstructionEvent(PlayerRef playerRef, ConstructionInfo constructionInfo, Tick tickCompleted) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionTick = Objects.requireNonNull(tickCompleted);
    }

    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }

    @Override
    public Tick getCompletionTick() {
        return completionTick;
    }
}
