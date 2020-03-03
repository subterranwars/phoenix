package de.stw.rest;

import de.stw.core.buildings.Building;
import de.stw.core.buildings.BuildingService;
import de.stw.core.buildings.Buildings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/buildings")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<Building> listBuildings() {
        return Buildings.ALL;
    }

    @PostMapping
    public void build(@RequestParam("userId") int userId, @RequestParam("buildingId") int buildingId) {
        LoggerFactory.getLogger(BuildingController.class).debug("I was invoked, userId={}, buildingId={}", userId, buildingId);
    }

    private class BuildRequest {
        private int userId;
        private int buildingId;
    }

}
