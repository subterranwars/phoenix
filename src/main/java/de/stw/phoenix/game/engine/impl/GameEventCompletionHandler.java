package de.stw.phoenix.game.engine.impl;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.Instant;

@Service
public class GameEventCompletionHandler {

    @Autowired
    PlayerService playerService;

    @Subscribe
    public void onEventCompletion(GameEvent event) {
		Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "No active session");
		final Notification notification = event.accept(new EventVisitor<Notification>() {

			@Override
			public Notification visit(ConstructionEvent constructionEvent) {
			final Building building = Buildings.findByRef(constructionEvent.getBuilding());
			return new Notification(Instant.now(), "Construction completed",
					building.getLabel() + " Lvl. " + constructionEvent.getLevelToBuild());
			}

			@Override
			public Notification visit(ResourceSearchEvent resourceSearchEvent) {
			return new Notification(Instant.now(), "Resource found",
				resourceSearchEvent.getResource().getName());
			}

			@Override
			public Notification visit(ResearchEvent researchEvent) {
				return new Notification(Instant.now(), "Research completed",
						researchEvent.getResearch().getLabel() + " Lvl. " + researchEvent.getLevelToResearch());
			}
		});
		event.getPlayer().addNotification(notification);
    }
}