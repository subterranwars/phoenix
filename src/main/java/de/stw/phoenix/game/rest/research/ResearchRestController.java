package de.stw.phoenix.game.rest.research;

import de.stw.phoenix.game.engine.research.api.Research;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.engine.research.api.ResearchService;
import de.stw.phoenix.game.engine.research.api.Researchs;
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

@RestController
@RequestMapping(path="/researchs")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class ResearchRestController {

    @Autowired
    private ResearchService researchService;

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<ResearchInfo> listConstructions(Principal principal) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        return researchService.listResearchs(player);
    }

    @PostMapping
    public void build(Principal principal, @RequestParam("researchId") int researchId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        final Research research = Researchs.findById(researchId);
        researchService.research(player, research);
    }
}
