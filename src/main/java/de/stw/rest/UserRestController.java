package de.stw.rest;

import com.google.common.collect.Maps;
import de.stw.core.clock.Clock;
import de.stw.core.user.User;
import de.stw.core.user.UserService;
import de.stw.rest.dto.UserDTO;
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
    private Clock clock;

    @GetMapping
    @RequestMapping("state")
    public UserDTO getPlayerState(Principal principal) {
        final String userName = principal.getName();
        final Optional<User> user = userService.find(userName);
        return new UserDTO(user.get(), clock.getCurrentTick());
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
