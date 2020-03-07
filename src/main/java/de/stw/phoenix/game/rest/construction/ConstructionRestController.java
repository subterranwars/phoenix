package de.stw.phoenix.game.rest.construction;

import de.stw.phoenix.game.data.buildings.Building;
import de.stw.phoenix.game.data.buildings.Buildings;
import de.stw.phoenix.game.engine.modules.construction.ConstructionInfo;
import de.stw.phoenix.game.engine.modules.construction.ConstructionService;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path="/constructions")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class ConstructionRestController {

    @Autowired
    private ConstructionService constructionService;

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<ConstructionInfo> listConstructions(Principal principal) {
        final ImmutablePlayer player = playerService.find(principal.getName()).orElseThrow(() -> new NoSuchElementException("Player with name '" + principal.getName() + "' not found"));
        return constructionService.listConstructions(player);
    }

    @PostMapping
    public void build(@RequestParam("playerId") int playerId, @RequestParam("buildingId") int buildingId) {
        final ImmutablePlayer player = playerService.find(playerId).orElseThrow(() -> new NoSuchElementException("Player with id '" + playerId + "' not found"));
        final Building building = Buildings.findById(buildingId);
        constructionService.build(player, building);
    }
}
