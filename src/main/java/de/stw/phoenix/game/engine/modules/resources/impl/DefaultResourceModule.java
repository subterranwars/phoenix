package de.stw.phoenix.game.engine.modules.resources.impl;

import de.stw.phoenix.game.engine.modules.resources.api.ResourceModule;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceProduction;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final List<ResourceProduction> productions = resourceService.getResourceProduction(player);
        for (ResourceProduction eachProduction : productions) {
            double amountToProduceInTick = eachProduction.getProductionValue() / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
            player.addResources(eachProduction.getStorage().getResource(), amountToProduceInTick);
        }
    }

    @Override
    public void afterUpdate(MutablePlayer player, Tick tick) {

    }
}
