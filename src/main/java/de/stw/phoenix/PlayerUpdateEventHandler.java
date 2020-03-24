package de.stw.phoenix;

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
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

@Service
public class PlayerUpdateEventHandler implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PlayerService playerService;

    @Subscribe
    public void onPlayerUpdated(final Player player) {
        final Player actualPlayer = playerService.get(player.getId());
        final List<ResourceOverview> resourceOverviews = resourceService.getResourceOverview(actualPlayer);
        final EnergyOverview energyOverview = resourceService.getEnergyOverview(actualPlayer);
        final PlayerDTO playerDTO = new PlayerDTO(actualPlayer, resourceOverviews, energyOverview);
        template.convertAndSendToUser(actualPlayer.getName(), "/updates", playerDTO);
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        final Player player = playerService.get(event.getUser().getName());
        onPlayerUpdated(player);
    }
}