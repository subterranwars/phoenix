package de.stw.core.gameloop;

import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.GameEvent;
import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.game.events.GameEventCompletionService;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import de.stw.rest.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);

    @Autowired
    private Clock clock;

    @Autowired
    private UserService userService;

    @Autowired
    private GameEventCompletionService completionService;

    public void loop() {
        printState();
        final Tick tick = clock.nextTick();

        // Produce resources
        final List<ResourceProduction> processes = userService.getUsers().stream().flatMap(p -> p.getResourceProduction().stream()).collect(Collectors.toList());
        for (ResourceProduction production : processes) {
            production.update(tick);
        }

        // Finish buildings
        final List<GameEvent> events = userService.getUsers().stream()
            .flatMap(u -> u.getEvents(tick).stream())
            .collect(Collectors.toList());
        for (GameEvent eachEvent : events) {
            completionService.complete(eachEvent);
        }
        // TODO MVR try not iterating over the same list twice
        userService.getUsers()
            .forEach(user -> user.getEvents(tick)
                .forEach(user::removeEvent));
    }

    private void printState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (User eachUser : userService.getUsers()) {
            for (ResourceStorage resource : eachUser.getResources()) {
                LOG.debug("{} {}: {}", eachUser.getName(), resource.getResource().getName(), resource.getAmount());
            }
            for (BuildingLevel buildingLevel : eachUser.getBuildings()) {
                LOG.debug("{} {}: {}", eachUser.getName(), buildingLevel.getBuilding().getLabel(), buildingLevel.getLevel());
            }
        }
    }

    public GameState getState() {
        return new GameState(userService.getUsers());
    }
}