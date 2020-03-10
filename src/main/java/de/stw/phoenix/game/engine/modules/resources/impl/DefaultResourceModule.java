package de.stw.phoenix.game.engine.modules.resources.impl;

import de.stw.phoenix.game.engine.modules.resources.api.ResourceModule;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultResourceModule implements ResourceModule {

    private final ResourceService resourceService;

    @Autowired
    public DefaultResourceModule(ResourceService resourceService) {
        this.resourceService = Objects.requireNonNull(resourceService);
    }

    @Override
    public void update(MutablePlayer player, Tick tick) {
        resourceService.getResourceProductions(player).forEach(gameBehaviour -> {
            gameBehaviour.update(player, tick);
        });
    }

    @Override
    public void afterUpdate(MutablePlayer player, Tick tick) {

    }
}
