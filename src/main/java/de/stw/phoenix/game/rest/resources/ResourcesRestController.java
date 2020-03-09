package de.stw.phoenix.game.rest.resources;

import de.stw.phoenix.game.data.resources.Resources;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceService;
import de.stw.phoenix.game.engine.modules.resources.api.ResourceSite;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.time.TimeDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path="/resources")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class ResourcesRestController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public void search(Principal principal, @RequestParam("resourceId") int resourceId, @RequestParam("hours") int hours) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        // TODO MVR ensure hours is > 1
        final ResourceSearchRequest resourceSearchRequest = new ResourceSearchRequest(player, Resources.findById(resourceId), TimeDuration.ofHours(hours));
        resourceService.search(resourceSearchRequest);
    }

    @GetMapping
    public List<ResourceSite> listResourceSites(Principal principal) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        return player.getResourceSites();
    }

    @DeleteMapping
    public void deleteResourceSite(Principal principal, @RequestParam("siteId") long resourceSiteId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        resourceService.deleteResourceSite(player, resourceSiteId);
    }

    @PutMapping
    public void updateDroneCount(Principal principal, @RequestParam("siteId") long resourceSiteId, @RequestParam("droneCount") int droneCount) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        resourceService.updateDroneCount(player, resourceSiteId, droneCount);
    }

}
