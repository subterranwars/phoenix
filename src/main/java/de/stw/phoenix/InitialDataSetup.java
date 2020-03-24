package de.stw.phoenix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.stw.phoenix.game.engine.buildings.BuildingRepository;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.construction.api.ConstructionInfo;
import de.stw.phoenix.game.engine.energy.Modifiers;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.research.api.ResearchInfo;
import de.stw.phoenix.game.engine.research.api.ResearchRepository;
import de.stw.phoenix.game.engine.research.api.Researchs;
import de.stw.phoenix.game.engine.resources.api.ResourceRepository;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.PlayerRepository;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.api.ResourceSite;
import de.stw.phoenix.game.player.api.ResourceStorage;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.test.TestRepository;
import de.stw.phoenix.game.test.TestService;
import de.stw.phoenix.game.time.ArtificialClock;
import de.stw.phoenix.game.time.ClockRepository;
import de.stw.phoenix.game.time.Moment;
import de.stw.phoenix.game.time.TimeDuration;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class InitialDataSetup {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ResearchRepository researchRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestService testService;

    @Autowired
    @Qualifier("myTaskExecutor")
    private TaskExecutor taskExecutor;

    @Autowired
    private ClockRepository clockRepository;

    @PostConstruct
    public void init() {
        Resources.ALL.forEach(r -> resourceRepository.save(r));
        Buildings.ALL.forEach(b -> buildingRepository.save(b));
        Researchs.ALL.forEach(r -> researchRepository.save(r));

        userRepository.save(User.builder().id(1).username("marskuh").email("marskuh@subterranwars.de").password("password").build());
        userRepository.save(User.builder().id(2).username("fafner").email("fafner@subterranwars.de").password("password").build());

        // Create player for each user
        userRepository.findAll().forEach(user -> playerRepository.save(
               Player.builder(user.getId(), user.getUsername())
                        .withDefaults()
                        .withBuilding(Buildings.Researchlab, 1)
                        .withResearch(Researchs.Construction, 1)
                        .withResourceSites(Lists.newArrayList(new ResourceSite(new ResourceStorage(Resources.Iron, 10000, 10000), 0)))
                        .withNotifications(Lists.newArrayList(
                               new Notification(Instant.now(), "Test", "Test Content", false)
                        ))
                       .withModifier(Modifiers.CRITICAL_ENERGY_LEVEL)
                        .build()));

        playerRepository.findAll().forEach(p -> playerService.modify(p.asPlayerRef(), player -> {
            player.addEvent(
                new ConstructionEvent(player.asPlayerRef(),
                    new ConstructionInfo(new BuildingLevel(Buildings.Researchlab, 1), Maps.newHashMap(), TimeDuration.ofSeconds(10)),
                    0, TimeDuration.ofSeconds(20), new Moment(0, TimeUnit.SECONDS))
            );
            player.addEvent(
                new ResearchEvent(player.asPlayerRef(),
                    new ResearchInfo(Researchs.Farming, 1, TimeDuration.ofSeconds(10)),
                    0, TimeDuration.ofSeconds(20), new Moment(0, TimeUnit.SECONDS))
            );
            player.addEvent(
                new ResourceSearchEvent(player.asPlayerRef(),
                    Resources.Oil,
                    new Moment(0, TimeUnit.SECONDS)));
        }));

        final ArtificialClock clock = new ArtificialClock(10, TimeUnit.SECONDS);
        clockRepository.save(clock);

//        final TestEntity test = new TestEntity();
//        test.setName("test");
//        test.setEmail("test@test.de");
//
//        test.add(Resources.Iron, 1000);
//        test.add(Resources.Stone, 1000);
//        test.add(Resources.Oil, 1000);
//        test.add(Resources.Food, 1000);
//
//        testRepository.save(test);
//
//        for (int i=0; i< 10; i++) {
//            taskExecutor.execute(() -> testService.updateCount(test.getId()));
//        }

    }
}
