package de.stw.phoenix;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.stw.phoenix.game.engine.energy.EnergyOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceOverview;
import de.stw.phoenix.game.engine.resources.api.ResourceService;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.rest.player.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PlayerUpdateEventHandler implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private EventBus eventBus;

    // Manually register for EventBus as this is an ApplicationListener
    // and therefore the BeanPostProcessor is not applied somehow :-/
    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    @Subscribe
    public void onPlayerUpdated(final Player player) {
        Preconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), "No active session");
        final List<ResourceOverview> resourceOverviews = resourceService.getResourceOverview(player);
        final EnergyOverview energyOverview = resourceService.getEnergyOverview(player);
        final PlayerDTO playerDTO = new PlayerDTO(player, resourceOverviews, energyOverview);
        template.convertAndSendToUser(player.getName(), "/updates", playerDTO);
    }

    @Override
    @Transactional
    public void onApplicationEvent(SessionSubscribeEvent event) {
        final Player player = playerService.get(event.getUser().getName());
        onPlayerUpdated(player);
    }
}