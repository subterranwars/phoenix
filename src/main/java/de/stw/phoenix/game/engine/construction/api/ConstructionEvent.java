package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.api.GameEvent;
import de.stw.phoenix.game.engine.api.EventVisitor;
import de.stw.phoenix.game.time.Moment;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
    private final ConstructionInfo constructionInfo;
    private final Moment completionMoment;

    public ConstructionEvent(ConstructionInfo constructionInfo, Moment completionMoment) {
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionMoment = Objects.requireNonNull(completionMoment);
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
