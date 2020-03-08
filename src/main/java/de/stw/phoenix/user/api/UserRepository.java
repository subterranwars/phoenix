package de.stw.phoenix.user.api;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> find(String userName);

    User save(User newUser);

    long count();

    List<User> findAll();

    Optional<User> lookup(String username, String password);
}
