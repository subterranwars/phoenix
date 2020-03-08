package de.stw.phoenix.user.impl;

import de.stw.phoenix.auth.impl.BCryptPasswordEncoder;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class DefaultUserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void before() {
        this.userRepository = new DefaultUserRepository(new BCryptPasswordEncoder());
    }

    @Test
    public void verifyDoesNotPersistUnsecurePasswords() {
        User user = User.builder().id(1).username("test").password("test").email("test@subterranwars.de").build();
        assertThat(user.getPassword().getValue(), Matchers.is("test"));

        user = userRepository.save(user);
        assertThat(user.getPassword().getValue(), Matchers.not(Matchers.is("test")));
    }

}