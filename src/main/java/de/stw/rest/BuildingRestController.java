package de.stw.rest;

import de.stw.core.buildings.Building;
import de.stw.core.buildings.Buildings;
import de.stw.core.buildings.ConstructionInfo;
import de.stw.core.buildings.ConstructionService;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path="/buildings")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class BuildingRestController {

    @Autowired
    private ConstructionService constructionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<ConstructionInfo> listBuildings(Principal principal) {
        final User user = userService.find(principal.getName()).orElseThrow(() -> new NoSuchElementException("User with name '" + principal.getName() + "' not found"));
        return constructionService.listBuildings(user);
    }

    @PostMapping
    public void build(@RequestParam("userId") int userId, @RequestParam("buildingId") int buildingId) {
        final User user = userService.find(userId).orElseThrow(() -> new NoSuchElementException("User with id '" + userId + "' not found"));
        final Building building = Buildings.findById(buildingId);
        constructionService.build(user, building);
    }
}
