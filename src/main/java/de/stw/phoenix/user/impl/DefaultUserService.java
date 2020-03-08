package de.stw.phoenix.user.impl;

import de.stw.phoenix.auth.api.PasswordEncoder;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import de.stw.phoenix.user.api.UserService;
import de.stw.phoenix.user.rest.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    public void create(UserCreateRequest userCreateRequest) {
        final User newUser = User.builder()
                .id(userRepository.count() + 1)
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword()).build();
        save(newUser);
    }

    // TODO MVR should be registered as a player as well
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


}
