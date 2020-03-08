package de.stw.phoenix.game.engine.modules.resources.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ResourceService {
    List<ResourceProduction> getResourceProduction(ImmutablePlayer player);

    void search(ResourceSearchRequest resourceSearchRequest);
}