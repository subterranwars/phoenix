package de.stw.phoenix.game.engine.resources.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.Objects;

public class ResourceSearchInfo {
    private final TimeDuration duration;
    private final Resource resource;

    @JsonIgnore
    private final Moment successMoment;

    public ResourceSearchInfo(Resource resource, TimeDuration duration, Moment successMoment) {
        this.resource = Objects.requireNonNull(resource);
        this.duration = Objects.requireNonNull(duration);
        this.successMoment = successMoment;
    }

    public boolean isSuccess() {
        return successMoment != null;
    }

    public TimeDuration getDuration() {
        return duration;
    }

    public Resource getResource() {
        return resource;
    }

    public Moment getSuccessMoment() {
        return successMoment;
    }
}
