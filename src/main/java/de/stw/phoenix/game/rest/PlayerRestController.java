package de.stw.phoenix.game.rest;

import de.stw.phoenix.game.time.Clock;
import de.stw.phoenix.game.engine.modules.resources.ResourceModule;
import de.stw.phoenix.game.engine.modules.resources.ResourceProduction;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/players")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ResourceModule resourceModule;

    @Autowired
    private Clock clock;

    @GetMapping
    @RequestMapping("state")
    public PlayerDTO getPlayerState(Principal principal) {
        final String playerName = principal.getName();
        final Optional<ImmutablePlayer> player = playerService.find(playerName);
        final List<ResourceProduction> resourceProductions = resourceModule.getResourceProduction(player.get())
                .stream()
                .map(production -> production.convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        return new PlayerDTO(player.get(), resourceProductions, clock.getCurrentTick());
    }

}
