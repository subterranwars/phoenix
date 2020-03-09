package de.stw.phoenix.game.engine.modules.resources.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.events.GameEvent;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.time.Moment;

import java.util.Objects;

public class ResourceSearchEvent implements GameEvent {

    @JsonIgnore
    private final PlayerRef playerRef;
    private final ResourceSearchInfo info;
    private final Moment completionMoment;

    public ResourceSearchEvent(PlayerRef playerRef, ResourceSearchInfo info, Moment completionMoment) {
        this.playerRef = Objects.requireNonNull(playerRef);
        this.info = Objects.requireNonNull(info);
        this.completionMoment = Objects.requireNonNull(completionMoment);
    }

    @Override
    public Moment getCompletionMoment() {
        return info.getSuccessMoment();
    }

    @Override
    public Moment getUserCompletionMoment() {
        return completionMoment;
    }

    public PlayerRef getPlayerRef() {
        return playerRef;
    }

    public ResourceSearchInfo getInfo() {
        return info;
    }
}
