package de.stw.phoenix.auth.impl;

import de.stw.phoenix.auth.api.AuthService;
import de.stw.phoenix.user.impl.DefaultUserRepository;
import org.junit.jupiter.api.Test;

// TODO MVR implement me
class DefaultAuthServiceTest {

    @Test
    public void verifyAuth() {
        final AuthService authService = new DefaultAuthService(new DefaultUserRepository());
    }

}