package de.stw.core.gameloop;

import de.stw.core.clock.Clock;
import de.stw.core.clock.Tick;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import de.stw.rest.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);

    @Autowired
    private Clock clock;

    @Autowired
    private UserService userService;

    private GameState state;

    @PostConstruct
    public void init() {
        updateState();
    }

    public void loop() {
        printState();
        final Tick tick = clock.nextTick();
        final List<ResourceProduction> processes = userService.getUsers().stream().flatMap(p -> p.getResourceProduction().stream()).collect(Collectors.toList());
        for (ResourceProduction production : processes) {
            production.update(tick);
        }
        updateState();
    }

    private void printState() {
        LOG.debug("Tick: {}", clock.getCurrentTick());
        for (User eachUser : userService.getUsers()) {
            for (ResourceStorage resource : eachUser.getResources()) {
                LOG.debug("{} {}: {}", eachUser.getName(), resource.getResource().getName(), resource.getAmount());
            }
        }
    }

    public synchronized void updateState() {
        this.state = new GameState(userService.getUsers());
    }

    public synchronized  GameState getState() {
        return state;
    }
}