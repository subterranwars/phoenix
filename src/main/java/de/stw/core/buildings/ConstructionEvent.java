package de.stw.core.buildings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.core.clock.Tick;
import de.stw.core.user.User;

import java.util.Objects;

// TODO MVR how to serialize?
public class ConstructionEvent implements GameEvent {
    @JsonIgnore
    private final User user;
    private final BuildingLevel buildingLevel;
    private final Tick completionTick;

    public ConstructionEvent(User user, BuildingLevel buildingLevel, Tick tickCompleted) {
        this.user = Objects.requireNonNull(user);
        this.buildingLevel = Objects.requireNonNull(buildingLevel);
        this.completionTick = Objects.requireNonNull(tickCompleted);
    }

    public User getUser() {
        return user;
    }

    public BuildingLevel getBuildingLevel() {
        return buildingLevel;
    }

    @Override
    public Tick getCompletionTick() {
        return completionTick;
    }
}
