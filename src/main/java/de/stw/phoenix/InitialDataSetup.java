package de.stw.phoenix;

import de.stw.phoenix.auth.api.PasswordEncoder;
import de.stw.phoenix.game.engine.buildings.BuildingRepository;
import de.stw.phoenix.game.engine.buildings.Buildings;
import de.stw.phoenix.game.engine.research.api.ResearchRepository;
import de.stw.phoenix.game.engine.research.api.Researchs;
import de.stw.phoenix.game.engine.resources.api.ResourceRepository;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.PlayerRepository;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.time.ClockRepository;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

import javax.annotation.PostConstruct;

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
    private ClockRepository clockRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initWrapper() {
        transactionOperations.execute(status -> {
            init();
            return null;
        });
    }

    private void init() {
        Resources.ALL.forEach(r -> resourceRepository.save(r));
        Buildings.ALL.forEach(b -> buildingRepository.save(b));
        Researchs.ALL.forEach(r -> researchRepository.save(r));

        userRepository.save(User.builder()
                .username("marskuh")
                .email("marskuh@subterranwars.de")
                .password(passwordEncoder.encode("password"))
                .build());
        userRepository.save(User.builder()
                .username("fafner")
                .email("fafner@subterranwars.de")
                .password(passwordEncoder.encode("password"))
                .build());

        // Create player for each user
        userRepository.findAll().forEach(user -> playerRepository.save(
               Player.builder(user.getId(), user.getUsername())
                        .withDefaults()
                        .build()));
    }
}
