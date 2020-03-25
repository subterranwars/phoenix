package de.stw.phoenix.game.rest.construction;

import de.stw.phoenix.game.engine.buildings.Building;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.construction.api.ConstructionService;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path="/constructions")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class ConstructionRestController {

    @Autowired
    private ConstructionService constructionService;

    @Autowired
    private PlayerService playerService;

    @GetMapping
    @Transactional
    public List<ConstructionInfo> listConstructions(Principal principal) {
        final Player player = playerService.get(principal.getName());
        return constructionService.listConstructions(player);
    }

    @PostMapping
    @Transactional
    public void build(Principal principal, @RequestParam("buildingId") int buildingId) {
        final Player player = playerService.get(principal.getName());
        final Building building = Buildings.findById(buildingId);
        constructionService.build(player, building);
    }
}
