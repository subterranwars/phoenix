package de.stw.rest;

import de.stw.core.resources.Building;
import de.stw.core.resources.Buildings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/buildings")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class BuildingController {

    @GetMapping
    public List<Building> listBuildings() {
        return Buildings.ALL;
    }
}
