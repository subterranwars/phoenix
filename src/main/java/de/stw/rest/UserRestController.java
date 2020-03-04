package de.stw.rest;

import com.google.common.collect.Maps;
import de.stw.core.gameloop.GameLoop;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/users")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameLoop loop;

    @GetMapping
    @RequestMapping("state")
    public PlayerState getPlayerState(Principal principal) {
        final String userName = principal.getName();
        return loop.getState().getPlayerState(userName);
    }

    @PostMapping
    @RequestMapping(path="authenticate")
    public Map<String, Object> authenticate(@RequestBody UserLoginRequest userLoginRequest) {
        final String token = userService.authenticate(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        final Optional<User> userOptional = userService.findByToken(token);
        final Map<String, Object> map = Maps.newHashMap();
        map.put("id", userOptional.get().getId());
        map.put("username", userOptional.get().getName());
        map.put("token", token);
        return map;
    }

    @PostMapping
    @RequestMapping(path="register")
    public void createUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.create(userCreateRequest);
    }

}
