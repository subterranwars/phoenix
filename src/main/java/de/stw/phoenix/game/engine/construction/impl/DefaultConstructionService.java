package de.stw.phoenix.game.engine.construction.impl;

import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.construction.api.ConstructionService;
import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayerAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultConstructionService implements ConstructionService {

    @Autowired
    private Clock clock;

    @Autowired
    private MutablePlayerAccessor playerAccessor;

    @Override
    public List<ConstructionInfo> listConstructions(final ImmutablePlayer player) {
        Objects.requireNonNull(player);
        final BuildingLevel hqBuilding = player.getBuilding(Buildings.Headquarter);
        return Buildings.ALL.stream()
                .map(player::getBuilding)
                .map(BuildingLevel::next)
                .map(bl -> new ConstructionInfo(bl, hqBuilding))
                .collect(Collectors.toList());
    }

    @Override
    public void build(final ImmutablePlayer player, final Building building) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(building);
        if (player.getConstructionEvent() == null) {
            final BuildingLevel userLevel = player.getBuilding(building);
            final ConstructionInfo constructionInfo = new ConstructionInfo(userLevel.next(), player.getBuilding(Buildings.Headquarter));
            if (player.canAfford(constructionInfo.getCosts())) {
                playerAccessor.modify(player, mutablePlayer -> {
                    // Enqueue
                    final Moment futureMoment = clock.getMoment(constructionInfo.getBuildTime());
                    final ConstructionEvent constructionEvent = new ConstructionEvent(constructionInfo, futureMoment);
                    mutablePlayer.addEvent(constructionEvent);

                    // Subtract resources
                    mutablePlayer.removeResources(constructionInfo.getCosts());
                });
            } else {
                // TODO MVR throw exception? Cannot afford?
            }
        } else {
            // TODO MVR error handling => not allowed while already constructing
        }
    }
}
