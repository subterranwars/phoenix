package de.stw.phoenix.game.rest.resources;

import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.player.api.Progress;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.GameEventTypes;

import java.util.Objects;

public class ResourceSearchEventDTO extends GameEventDTO {
    private final Resource resource;

    public ResourceSearchEventDTO(final Progress progress, Resource resource) {
        super(GameEventTypes.ResourceSearch, progress);
        this.resource = Objects.requireNonNull(resource);
    }

    public Resource getResource() {
        return resource;
    }

}
