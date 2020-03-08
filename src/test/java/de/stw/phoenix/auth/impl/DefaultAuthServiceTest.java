package de.stw.phoenix.auth.impl;

import de.stw.phoenix.auth.api.Token;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import de.stw.phoenix.user.impl.DefaultUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultAuthServiceTest {

    private UserRepository userRepository;
    private DefaultAuthService authService;

    @BeforeEach
    public void before() {
        final User user = User.builder().id(1).username("test").password("test").email("test@subterranwars.de").build();
        userRepository = new DefaultUserRepository();
        userRepository.save(user);
        authService = new DefaultAuthService(userRepository);
    }

    @Test
    public void verifyAuth() {
        final Token token = authService.authenticate("test", "test");
        assertThat(token, notNullValue());
        assertThat(token.isValid(), is(true));
        assertThat(token.getToken(), notNullValue());

        final Optional<User> user = authService.findUser(token.getToken());
        assertThat(user.isPresent(), is(true));
        assertThat(user.get().getUsername(), is("test"));
    }

    @Test
    public void verifyInvalidCredentials() {
        assertThrows(BadCredentialsException.class, () -> authService.authenticate("test", "test1234"));
    }

    @Test
    public void verifyInvalidate() {
        final Token token = authService.authenticate("test", "test");
        authService.invalidate(token);
        assertThat(authService.findUser(token.getToken()).isPresent(), is(false));
        assertThat(authService.findToken("test").isPresent(), is(false));
    }

    @Test
    public void verifyInvalidateOnReauth() {
        final Token token = authService.authenticate("test", "test");
        final Token anotherToken = authService.authenticate("test", "test");
        assertThat(token, notNullValue());
        assertThat(anotherToken, notNullValue());
        assertThat(token.getToken(), not(is(anotherToken.getToken())));

        // Ensure it was invalidated
        assertThat(authService.findUser(token.getToken()).isPresent(), is(false));
        assertThat(authService.findUser(anotherToken.getToken()).isPresent(), is(true));

        // Ensure mapping for user updates properly
        assertThat(authService.findToken("test").get(), is(anotherToken));
    }

}