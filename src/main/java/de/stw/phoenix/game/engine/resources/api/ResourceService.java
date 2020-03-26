package de.stw.phoenix.game.engine.resources.api;

import de.stw.phoenix.game.engine.energy.EnergyOverview;
import de.stw.phoenix.game.player.impl.Player;

import java.util.List;

public interface ResourceService {
    List<ResourceOverview> getResourceOverview(Player player);

    void search(ResourceSearchRequest resourceSearchRequest);

    void deleteResourceSite(Player player, long resourceSiteId);

    void updateDroneCount(Player player, long resourceSiteId, int droneCount);

    EnergyOverview getEnergyOverview(Player player);
}
