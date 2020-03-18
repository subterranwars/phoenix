package de.stw.phoenix.game.rest;

import com.google.common.eventbus.EventBus;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
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
    public void deleteNotification(Principal principal, @RequestParam("notificationId") long notificationId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        playerService.modify(player, mutablePlayer -> mutablePlayer.removeNotificationById(notificationId));
        eventBus.post(player);
    }
    
    @PatchMapping
    public void markAsRead(Principal principal, @RequestParam("notificationId") long notificationId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        playerService.modify(player, mutablePlayer -> mutablePlayer.markNotificationAsRead(notificationId));
        eventBus.post(player);
    };
}