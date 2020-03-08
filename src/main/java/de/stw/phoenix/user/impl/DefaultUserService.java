package de.stw.phoenix.user.impl;

import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import de.stw.phoenix.user.api.UserService;
import de.stw.phoenix.user.rest.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(UserCreateRequest userCreateRequest) {
        if (userRepository.find(userCreateRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        // TODO MVR should be registered as a player as well
        final User newUser = User.builder()
                .id(userRepository.count() + 1)
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword()).build();
        userRepository.save(newUser);
    }


}
