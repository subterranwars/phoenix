package de.stw.phoenix.game.rest;

import java.security.Principal;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;

@RestController
@RequestMapping(path="/notifications")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class NotificationRestController {
    @Autowired
    private PlayerService playerService;
    
    @DeleteMapping
    public void deleteNotification(Principal principal, @RequestParam("notificationId") long notificationId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        playerService.modify(player, new Consumer<MutablePlayer>() {
	    @Override
	    public void accept(MutablePlayer mutablePlayer) {
		mutablePlayer.removeNotificationById(notificationId);
	    }            
        });
    }
    
    @PatchMapping
    public void markAsRead(Principal principal, @RequestParam("notificationId") long notificationId) {
        final ImmutablePlayer player = playerService.get(principal.getName());
        playerService.modify(player, new Consumer<MutablePlayer>() {
	    @Override
	    public void accept(MutablePlayer mutablePlayer) {
		mutablePlayer.markNotificationAsRead(notificationId);
	    }            
        });

    };
}