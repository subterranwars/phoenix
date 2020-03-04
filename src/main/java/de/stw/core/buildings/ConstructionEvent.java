package de.stw.core.buildings;

import de.stw.core.clock.Tick;
import de.stw.core.user.User;

import java.util.Objects;

public class ConstructionEvent implements GameEvent {
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

    @Override
    public boolean isComplete(Tick currentTick) {
        return false;
    }
}
