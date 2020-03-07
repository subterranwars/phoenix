package de.stw.phoenix.game.engine.modules.resources;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.clock.TimeConstants;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultResourceModule implements ResourceModule {

    @Override
    public List<ResourceProduction> getResourceProduction(final ImmutablePlayer player) {
        return player.getResources().stream()
                .map(storage -> new ResourceProduction(player, storage, 60))
                .collect(Collectors.toList());
    }

    @Override
    public void update(MutablePlayer player, Tick tick) {
        final List<ResourceProduction> productions = getResourceProduction(player);
        for (ResourceProduction eachProduction : productions) {
            double amountToProduceInTick = eachProduction.getProductionValue() / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
            player.addResources(eachProduction.getStorage().getResource(), amountToProduceInTick);
        }
    }

    @Override
    public void afterUpdate(MutablePlayer player, Tick tick) {

    }
}
