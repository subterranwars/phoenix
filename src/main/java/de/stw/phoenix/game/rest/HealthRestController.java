package de.stw.phoenix.game.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthRestController {

    @GetMapping
    public boolean getHealth() {
        return true;
    }
}
