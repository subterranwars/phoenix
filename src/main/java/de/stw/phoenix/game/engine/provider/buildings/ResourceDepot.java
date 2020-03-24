package de.stw.phoenix.game.engine.provider.buildings;

import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceDepot implements GameElementProvider {

    @Autowired
    private PlayerService playerService;

    @Subscribe
    public void onConstructionCompleted(ConstructionEvent constructionEvent) {
        if (Buildings.findByRef(constructionEvent.getConstructionInfo().getBuilding()) == Buildings.Resourcedepot) {
            playerService.modify(constructionEvent.getPlayerRef(), mutablePlayer -> {
                BuildingLevel building = mutablePlayer.getBuilding(Buildings.Resourcedepot);
                final long maxStorage = Resources.MAX_STORAGE_CAPACITY + Resources.STORAGE_CAPACITY_GAIN * building.getLevel();
                mutablePlayer.updateResourceStorage(maxStorage);
            });
        }
    }

    @Override
    public void registerElements(MutableContext context, Player player) {

    }
}
