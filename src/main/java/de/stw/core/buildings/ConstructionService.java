package de.stw.core.buildings;

import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ConstructionService {

    @Autowired
    private Clock clock;

    public List<ConstructionInfo> listBuildings(final User user) {
        Objects.requireNonNull(user);
        final BuildingLevel hqBuilding = user.getBuilding(Buildings.Headquarter);
        return Buildings.ALL.stream()
                .map(user::getBuilding)
                .map(BuildingLevel::next)
                .map(bl -> new ConstructionInfo(bl, hqBuilding))
                .collect(Collectors.toList());
    }

    public void build(final User user, final Building building) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(building);
        if (user.getConstructionEvent() == null) {
            final BuildingLevel userLevel = user.getBuilding(building);
            final ConstructionInfo ci = new ConstructionInfo(userLevel.next(), user.getBuilding(Buildings.Headquarter));
            if (user.canAfford(ci.getCosts())) {
                enqueue(user, ci);
                user.removeResources(ci.getCosts());
            } else {
                // TODO MVR throw exception? Cannot afford?
            }
        } else {
            // TODO MVR error handling => not allowed while already constructing
        }
    }

    private void enqueue(User user, ConstructionInfo constructionInfo) {
        final Tick futureTick = clock.getTick(constructionInfo.getBuildTimeInSeconds(), TimeUnit.SECONDS);
        final ConstructionEvent constructionEvent = new ConstructionEvent(user, constructionInfo, futureTick);
        user.addConstruction(constructionEvent);
    }
}
