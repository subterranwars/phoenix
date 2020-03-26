package de.stw.phoenix.game.rest.player;

import de.stw.phoenix.game.engine.energy.EnergyOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/players")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    @RequestMapping("state")
    @Transactional
    public PlayerDTO getPlayerState(Principal principal) {
        final String playerName = principal.getName();
        final Optional<Player> player = playerService.find(playerName);
        final List<ResourceOverview> resourceOverviews = resourceService.getResourceOverview(player.get());
        final EnergyOverview energyOverview = resourceService.getEnergyOverview(player.get());
        return new PlayerDTO(player.get(), resourceOverviews, energyOverview);
    }

}
