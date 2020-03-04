package de.stw.rest;

import de.stw.core.buildings.BuildingLevel;
import de.stw.core.buildings.Buildings;
import de.stw.core.buildings.ConstructionService;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/buildings")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class BuildingRestController {

    @Autowired
    private ConstructionService constructionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<BuildingLevel> listBuildings(Principal principal) {
        final String username = principal.getName();
        final User user = userService.find(username).get();
        return Buildings.ALL.stream().map(user::getBuilding).collect(Collectors.toList());
    }

    @PostMapping
    public void build(@RequestParam("userId") int userId, @RequestParam("buildingId") int buildingId) {
        constructionService.build(userId, buildingId);
    }
}
