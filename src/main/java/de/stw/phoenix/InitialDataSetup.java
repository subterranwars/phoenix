package de.stw.phoenix;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.resources.api.ResourceSite;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.ImmutablePlayerImpl;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitialDataSetup {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerService playerService;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder().id(1).username("marskuh").email("marskuh@subterranwars.de").password("password").build());
        userRepository.save(User.builder().id(2).username("fafner").email("fafner@subterranwars.de").password("password").build());

        // Create player for each user
        userRepository.findAll().forEach(user -> playerService.save(
                ImmutablePlayerImpl.builder(user.getId(), user.getUsername())
                        .withDefaults()
                        .withBuilding(Buildings.Resourcefacility, 1)
                        .withResourceSites(Lists.newArrayList(
                                new ResourceSite(1, new ImmutableResourceStorage(Resources.Iron, 10000, 10000), 10),
                                new ResourceSite(2, new ImmutableResourceStorage(Resources.Oil, 10000, 10000), 0)
                        ))
                        .build()));
    }
}
