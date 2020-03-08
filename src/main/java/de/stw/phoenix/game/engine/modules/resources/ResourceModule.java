package de.stw.phoenix.game.engine.modules.resources;

import de.stw.phoenix.game.engine.api.GameModule;
import de.stw.phoenix.game.player.api.ImmutablePlayer;

import java.util.List;

public interface ResourceModule extends GameModule {
    List<ResourceProduction> getResourceProduction(ImmutablePlayer player);
}
