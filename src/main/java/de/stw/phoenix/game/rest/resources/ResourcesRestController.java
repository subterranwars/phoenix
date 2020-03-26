package de.stw.phoenix.game.rest.resources;

import de.stw.phoenix.game.engine.resources.api.ResourceSearchRequest;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.api.ResourceSite;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void search(Principal principal, @RequestParam("resourceId") int resourceId) {
        final Player player = playerService.get(principal.getName());
        final ResourceSearchRequest resourceSearchRequest = new ResourceSearchRequest(player, Resources.findById(resourceId));
        resourceService.search(resourceSearchRequest);
    }

    @GetMapping
    @Transactional
    public List<ResourceSite> listResourceSites(Principal principal) {
        final Player player = playerService.get(principal.getName());
        return player.getResourceSites();
    }

    @DeleteMapping
    @Transactional
    public void deleteResourceSite(Principal principal, @RequestParam("siteId") long resourceSiteId) {
        final Player player = playerService.get(principal.getName());
        resourceService.deleteResourceSite(player, resourceSiteId);
    }

    @PutMapping
    @Transactional
    public void updateDroneCount(Principal principal, @RequestParam("siteId") long resourceSiteId, @RequestParam("droneCount") int droneCount) {
        final Player player = playerService.get(principal.getName());
        resourceService.updateDroneCount(player, resourceSiteId, droneCount);
    }

}
