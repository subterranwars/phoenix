package de.stw.rest;

import de.stw.core.GameLoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class GameStateController {

    @Autowired
    private GameLoop loop;

    @GetMapping("/state")
    public GameState getState() {
        return loop.getState();
    }

}
