package de.stw.phoenix.game.engine.resources.impl;

import de.stw.phoenix.game.engine.api.events.GameEvent;
import de.stw.phoenix.game.engine.api.events.EventVisitor;
import de.stw.phoenix.game.engine.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.time.Moment;

import java.util.Objects;

public class ResourceSearchEvent implements GameEvent {

    private final ResourceSearchInfo info;
    private final Moment completionMoment;

    public ResourceSearchEvent(ResourceSearchInfo info, Moment completionMoment) {
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

    public ResourceSearchInfo getInfo() {
        return info;
    }

    @Override
    public <T> T accept(EventVisitor<T> visitor) {
        Objects.requireNonNull(visitor);
        return visitor.visit(this);
    }
}
