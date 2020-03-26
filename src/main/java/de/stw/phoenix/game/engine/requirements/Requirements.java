package de.stw.phoenix.game.engine.requirements;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.research.api.Research;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Requirements {

    private Requirements() {}

    public static final Requirement and(Requirement... requirements) {
        Objects.requireNonNull(requirements);
        Preconditions.checkArgument(requirements.length > 0, "Requirements must not be empty");
        for (Requirement requirement : requirements) {
            Objects.requireNonNull(requirement);
        }
        final List<Requirement> requirementList = Arrays.asList(requirements);
        if (requirementList.size() == 1) {
            return requirementList.get(0);
        }
        return new AndRequirement(requirementList);
    }

    public static final Requirement research(Research research, int level) {
        Objects.requireNonNull(research);
        return new ResearchRequirement(research, level);
    }

    public static final Requirement building(Building building, int level) {
        Objects.requireNonNull(building);
        return new BuildingRequirement(building, level);
    }
}
