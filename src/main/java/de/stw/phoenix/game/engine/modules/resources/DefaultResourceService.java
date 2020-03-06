package de.stw.phoenix.game.engine.modules.resources;

import de.stw.phoenix.game.clock.Tick;
import de.stw.phoenix.game.clock.TimeConstants;
import de.stw.phoenix.game.engine.api.MutablePlayerAccessor;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultResourceService implements ResourceService {

    private final MutablePlayerAccessor playerAccessor;

    @Autowired
    public DefaultResourceService(final MutablePlayerAccessor playerAccessor) {
        this.playerAccessor = Objects.requireNonNull(playerAccessor);
    }

    @Override
    public List<ResourceProduction> getResourceProduction(final ImmutablePlayer player) {
        return player.getResources().stream()
                .map(storage -> new ResourceProduction(player, storage, 60))
                .collect(Collectors.toList());
    }

    @Override
    public void update(ImmutablePlayer player, Tick tick) {
        playerAccessor.requestAccess(player, mutablePlayer -> {
            final List<ResourceProduction> productions = getResourceProduction(player);
            for (ResourceProduction eachProduction : productions) {
                double amountToProduceInTick = eachProduction.getProductionValue() / TimeConstants.MILLISECONDS_PER_HOUR * tick.getDelta();
                mutablePlayer.addResources(eachProduction.getStorage().getResource(), amountToProduceInTick);
            }
        });
        getResourceProduction(player);
    }

    @Override
    public void afterUpdate(ImmutablePlayer player, Tick tick) {

    }
}
