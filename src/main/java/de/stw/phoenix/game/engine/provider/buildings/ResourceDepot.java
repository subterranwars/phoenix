package de.stw.phoenix.game.engine.provider.buildings;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ResourceDepot implements GameElementProvider {

    @Subscribe
    public void onConstructionCompleted(ConstructionEvent constructionEvent) {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "No active session");
        if (Buildings.findByRef(constructionEvent.getConstructionInfo().getBuilding()) == Buildings.Resourcedepot) {
            final Player player = constructionEvent.getPlayer();
            final BuildingLevel building = player.getBuilding(Buildings.Resourcedepot);
            final long maxStorage = Resources.MAX_STORAGE_CAPACITY + Resources.STORAGE_CAPACITY_GAIN * building.getLevel();
            player.updateResourceStorage(maxStorage);
        }
    }

    @Override
    public void registerElements(MutableContext context, Player player) {

    }
}
