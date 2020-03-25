package de.stw.phoenix.game.engine.provider.buildings;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.api.GameElementProvider;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.engine.api.Phases;
import de.stw.phoenix.game.engine.api.PlayerUpdate;
import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.engine.research.api.Researchs;
import de.stw.phoenix.game.engine.research.api.calculator.ResearchTimeCalculator;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Tick;
import de.stw.phoenix.game.time.TimeDuration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResearchProgress implements GameElementProvider {

    @Autowired
    private ResearchTimeCalculator researchTimeCalculator;

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
                final ResearchEvent researchEvent = player.findSingleEvent(ResearchEvent.class).get();
                final ResearchInfo researchInfo = researchEvent.getResearchInfo();

                // Calculate progress
                final TimeDuration timeDuration = researchTimeCalculator.calculateResearchTime(researchInfo.getResearch(), researchInfo.getLevelToResearch(), player);
                final double progressPerTick = (double) tick.getDelta() / (double) timeDuration.getMilliseconds();
                final double totalProgress = researchEvent.getProgress().getValue() + progressPerTick;
                final double progressPerSecond = progressPerTick / tick.getDelta() * 1000;
                final double leftProgress = Math.max(1 - totalProgress, 0);
                final long estimatedSecondsLeft = Math.round(leftProgress / progressPerSecond);
                final TimeDuration estimatedDuration = TimeDuration.ofSeconds(estimatedSecondsLeft);

                LoggerFactory.getLogger(ResearchProgress.this.getClass()).info("Progress: {}/{}, estimated duration left: {} sec", progressPerTick, totalProgress, estimatedDuration.getSeconds());

                // Updated event
                final ResearchEvent updatedEvent = new ResearchEvent(player, researchInfo, researchEvent.getProgress().getValue() + progressPerTick, estimatedDuration, tick.toMoment());
                player.removeEvent(researchEvent);
                player.addEvent(updatedEvent);
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                final Optional<ResearchEvent> singleEvent = player.findSingleEvent(ResearchEvent.class);
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
                final ResearchEvent event = player.findSingleEvent(ResearchEvent.class).get();
                final ResearchInfo researchInfo = event.getResearchInfo();
                final Research research = Researchs.findByRef(researchInfo.getResearch());
                LoggerFactory.getLogger(getClass()).info("Completing research event. User: {}, Building: {}, Level: {}", player.getName(), research.getLabel(), researchInfo.getLevelToResearch());
                final ResearchLevel newLevel = new ResearchLevel(research, researchInfo.getLevelToResearch());
                player.setResearch(newLevel);

                // TODO MVR is this really how we want to do things?
                eventBus.post(event);
                player.removeEvent(event);
            }

            @Override
            public boolean isActive(Player player, Tick currentTick) {
                final ResearchEvent researchEvent = player.findSingleEvent(ResearchEvent.class).orElse(null);
                return researchEvent != null && researchEvent.isFinished();
            }
        });
    }
}
