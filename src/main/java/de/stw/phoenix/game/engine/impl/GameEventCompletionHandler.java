package de.stw.phoenix.game.engine.impl;

import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class GameEventCompletionHandler {
    final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    PlayerService playerService;

    @Subscribe
    public void onEventCompletion(GameEvent event) {
		final Notification notification = event.accept(new EventVisitor<Notification>() {

			@Override
			public Notification visit(ConstructionEvent constructionEvent) {
			ConstructionInfo info = constructionEvent.getConstructionInfo();
			return new Notification(secureRandom.nextInt(), Instant.now(), "Construction completed",
				info.getBuilding().getLabel() + " Lvl. " + info.getLevelToBuild());
			}

			@Override
			public Notification visit(ResourceSearchEvent resourceSearchEvent) {
			return new Notification(secureRandom.nextInt(), Instant.now(), "Resource found",
				resourceSearchEvent.getResource().getName());
			}

			@Override
			public Notification visit(ResearchEvent researchEvent) {
				final ResearchInfo researchInfo = researchEvent.getResearchInfo();
				return new Notification(secureRandom.nextInt(), Instant.now(), "Research completed",
						researchInfo.getResearch().getLabel() + " Lvl. " + researchInfo.getLevelToResearch());
			}
		});
		ImmutablePlayer player = playerService.get(event.getPlayerRef().getId());
		playerService.modify(player, (mutablePlayer) -> {
			mutablePlayer.addNotification(notification);
		});
    }
}