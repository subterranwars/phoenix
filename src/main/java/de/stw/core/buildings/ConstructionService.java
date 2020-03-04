package de.stw.core.buildings;

import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ConstructionService {

    @Autowired
    private UserService userService;

    @Autowired
    private Clock clock;

    public void build(int userId, int buildingId) {
        final Optional<User> userOptional = userService.find(userId);
        if (!userOptional.isPresent()) {
            throw new NoSuchElementException(String.format("No user with id '%s' found", userId));
        }
        final Optional<Building> buildingOptional = find(buildingId);
        if (!buildingOptional.isPresent()) {
            throw new NoSuchElementException(String.format("No building with id '%s' found", buildingId));
        }
        final User user = userOptional.get();
        if (user.getConstructionEvent() == null) {
            final Building building = buildingOptional.get();
            final BuildingLevel userLevel = user.getBuilding(building);
            if (user.canAfford(userLevel.getCosts())) {
                enqueue(user, userLevel);
                user.removeResources(userLevel.getCosts());
            } else {
                // TODO MVR throw exception? Cannot afford?
            }
        } else {
            // TODO MVR error handling => not allowed while already constructing
        }
    }

    private void enqueue(User user, BuildingLevel buildingLevel) {
        final int constructionTime = 60000; // ms
        final Tick futureTick = clock.getTick(constructionTime, TimeUnit.MILLISECONDS);
        final ConstructionEvent constructionEvent = new ConstructionEvent(user, buildingLevel.next(), futureTick);
        user.addConstruction(constructionEvent);
    }

    private Optional<Building> find(int buildingId) {
        return Buildings.ALL.stream().filter(b -> b.getId() == buildingId).findAny();
    }
}
