package de.stw.phoenix.game.engine.research.impl;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.engine.research.api.ResearchService;
import de.stw.phoenix.game.engine.research.api.Researchs;
import de.stw.phoenix.game.engine.research.api.calculator.ResearchTimeCalculator;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultResearchService implements ResearchService {

    @Autowired
    private ResearchTimeCalculator researchTimeCalculator;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Clock clock;

    @Override
    @Transactional
    public List<ResearchInfo> listResearchs(Player player) {
        Objects.requireNonNull(player);
        return Researchs.ALL.stream()
                .map(player::getResearch)
                .map(ResearchLevel::next)
                .map(rl -> {
                    final TimeDuration researchTime = researchTimeCalculator.calculateResearchTime(rl, player);
                    return new ResearchInfo(rl, researchTime);
                })
                .collect(Collectors.toList());
    }

    // TODO MVR ensure research is only performed if user has researchlab
    @Override
    @Transactional
    public void research(Player player, Research research) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(research);
        if (!player.findSingleEvent(ResearchEvent.class).isPresent()) {
            final ResearchLevel nextLevel = player.getResearch(research).next();
            final TimeDuration researchTime = researchTimeCalculator.calculateResearchTime(nextLevel, player);
            final ResearchInfo researchInfo =  new ResearchInfo(nextLevel, researchTime);
            // Enqueue
            final ResearchEvent researchEvent = new ResearchEvent(
                    player,
                    researchInfo,
                    0,
                    researchInfo.getResearchTime(),
                    clock.getCurrentTick().toMoment());
            player.addEvent(researchEvent);
            eventBus.post(player);
        } else {
            // TODO MVR error handling => not allowed while already constructing
        }
    }
}
