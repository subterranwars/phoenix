package de.stw.phoenix.game.rest;

import de.stw.phoenix.game.data.resources.Resources;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/resources")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class ResourcesRestController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PlayerService playerService;

    // TODO MVR this allows to create jobs for other players :ups:
    @PostMapping
    public void search(@PathVariable("playerId") int playerId, @PathVariable("resourceId") int resourceId, @PathVariable("hours") int hours) {
        final ImmutablePlayer player = playerService.get(playerId);
        // TODO MVR ensure hours is > 1
        final ResourceSearchRequest resourceSearchRequest = new ResourceSearchRequest(player, Resources.findById(resourceId), TimeDuration.ofHours(hours));
        resourceService.search(resourceSearchRequest);
    }

}
