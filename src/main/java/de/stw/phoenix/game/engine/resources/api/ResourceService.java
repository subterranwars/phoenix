package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ResourceService {
    List<ResourceOverview> getResourceProduction(ImmutablePlayer player);

    void search(ResourceSearchRequest resourceSearchRequest);

    void deleteResourceSite(ImmutablePlayer player, long resourceSiteId);

    void updateDroneCount(ImmutablePlayer player, long resourceSiteId, int droneCount);
}
