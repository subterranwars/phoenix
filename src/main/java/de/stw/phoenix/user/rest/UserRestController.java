package de.stw.phoenix.user.rest;

import de.stw.phoenix.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping
    @RequestMapping(path="register")
    public void createUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.create(userCreateRequest);
    }
}
