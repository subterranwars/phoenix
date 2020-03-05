package de.stw.core.buildings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.core.clock.Tick;
import de.stw.core.user.User;

import java.util.Objects;

// TODO MVR how to serialize?
public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final User user;
    private final ConstructionInfo constructionInfo;
    private final Tick completionTick;

    public ConstructionEvent(User user, ConstructionInfo constructionInfo, Tick tickCompleted) {
        this.user = Objects.requireNonNull(user);
        this.constructionInfo = Objects.requireNonNull(constructionInfo);
        this.completionTick = Objects.requireNonNull(tickCompleted);
    }

    public User getUser() {
        return user;
    }

    public ConstructionInfo getConstructionInfo() {
        return constructionInfo;
    }

    @Override
    public Tick getCompletionTick() {
        return completionTick;
    }
}
