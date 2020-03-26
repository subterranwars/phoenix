package de.stw.phoenix.game.engine.provider.buildings;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionTimeCalculator;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConstructionProgress implements GameElementProvider {

    @Autowired
    private ConstructionTimeCalculator constructionTimeCalculator;

    @Autowired
    private EventBus eventBus;

    @Override
    public void registerElements(MutableContext context, Player player) {
        context.add(new PlayerUpdate() {

            @Override
            public int getPhase() {
                return Phases.PreFinishEvents;
            }

            @Override
            public void update(Player player, Tick tick) {
                final ConstructionEvent constructionEvent = player.findSingleEvent(ConstructionEvent.class).get();

                // Calculate progress
                final TimeDuration timeDuration = constructionTimeCalculator.calculateConstructionTime(constructionEvent.getBuilding(), constructionEvent.getLevelToBuild(), player);
                final double progressPerTick = (double) tick.getDelta() / (double) timeDuration.getMilliseconds();
                final double totalProgress = constructionEvent.getProgress().getValue() + progressPerTick;
                final double progressPerSecond = progressPerTick / tick.getDelta() * 1000;
                final double leftProgress = Math.max(1 - totalProgress, 0);
                final long estimatedSecondsLeft = Math.round(leftProgress / progressPerSecond);
                final TimeDuration estimatedDuration = TimeDuration.ofSeconds(estimatedSecondsLeft);

                // Updated event
                LoggerFactory.getLogger(ConstructionProgress.this.getClass()).info("Progress: {}/{}, estimated duration left: {} sec", progressPerTick, totalProgress, estimatedDuration.getSeconds());
                constructionEvent.updateProgress(progressPerTick, estimatedDuration, tick.toMoment());
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                final Optional<ConstructionEvent> singleEvent = player.findSingleEvent(ConstructionEvent.class);
                return singleEvent.isPresent() && !singleEvent.get().isFinished();
            }
        });
        context.add(new PlayerUpdate() {
            @Override
            public int getPhase() {
                return Phases.FinishEvents;
            }

            @Override
            public void update(Player player, Tick tick) {
                final ConstructionEvent event = player.findSingleEvent(ConstructionEvent.class).get();
                LoggerFactory.getLogger(getClass()).info("Completing construction event. User: {}, Building: {}, Level: {}", player.getName(), event.getBuilding().getLabel(), event.getLevelToBuild());
                final BuildingLevel newLevel = new BuildingLevel(event.getBuilding(), event.getLevelToBuild());
                player.setBuilding(newLevel);

                // TODO MVR is this really how we want to do things?
                eventBus.post(event);
                player.removeEvent(event);
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                final ConstructionEvent constructionEvent = player.findSingleEvent(ConstructionEvent.class).orElse(null);
                return constructionEvent != null && constructionEvent.isFinished();
            }
        });
    }
}
