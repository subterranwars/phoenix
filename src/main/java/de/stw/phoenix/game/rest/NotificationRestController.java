package de.stw.phoenix.game.rest;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.player.api.PlayerService;
import de.stw.phoenix.game.player.impl.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path="/notifications")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class NotificationRestController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private EventBus eventBus;

    @DeleteMapping
    // TODO MVR move to own service
    public void deleteNotification(Principal principal, @RequestParam("notificationId") long notificationId) {
        final Player player = playerService.get(principal.getName());
        playerService.modify(player, mutablePlayer -> mutablePlayer.removeNotificationById(notificationId));
        eventBus.post(player);
    }
    
    @PatchMapping
    // TODO MVR move to own service
    public void markAsRead(Principal principal, @RequestParam("notificationId") long notificationId) {
        final Player player = playerService.get(principal.getName());
        playerService.modify(player, mutablePlayer -> mutablePlayer.markNotificationAsRead(notificationId));
        eventBus.post(player);
    };
}