package de.stw.phoenix.game.engine.modules.resources;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ResourceService extends GameModule {
    List<ResourceProduction> getResourceProduction(ImmutablePlayer player);

    void update(ImmutablePlayer player, Tick tick);
}
