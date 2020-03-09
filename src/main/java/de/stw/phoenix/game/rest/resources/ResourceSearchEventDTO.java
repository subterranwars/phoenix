package de.stw.phoenix.game.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.stw.phoenix.game.data.resources.Resource;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchInfo;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.GameEventTypes;

import java.util.Objects;

public class ResourceSearchEventDTO extends GameEventDTO {
    private final Resource resource;

    @JsonProperty("durationInHours")
    private final long duration;

    public ResourceSearchEventDTO(long completedInSeconds, ResourceSearchInfo info) {
        super(GameEventTypes.ResourceSearch, completedInSeconds);
        Objects.requireNonNull(info);
        this.resource = info.getResource();
        this.duration = info.getDuration().getHours();
    }

    public Resource getResource() {
        return resource;
    }

    public long getDuration() {
        return duration;
    }
}
