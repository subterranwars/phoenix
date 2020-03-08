package de.stw.phoenix.auth.rest;

import de.stw.phoenix.auth.api.AuthService;
import de.stw.phoenix.auth.api.Token;
import de.stw.phoenix.auth.api.UserAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*", allowedHeaders = "*") /* TODO MVR this is bad */
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public Token authenticate(@RequestBody UserAuthRequest userAuthRequest) {
        final Token token = authService.authenticate(userAuthRequest);
        return token;
    }

}
