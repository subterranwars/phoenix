package de.stw.phoenix.user.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.user.api.User;
import de.stw.phoenix.user.api.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultUserRepository implements UserRepository {

    private List<User> users = Lists.newArrayList();

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Optional<User> find(String userName) {
        Objects.requireNonNull(userName);
        return users.stream().filter(u -> userName.equalsIgnoreCase(u.getUsername())).findAny();
    }

    @Override
    public void save(User newUser) {
        users.add(newUser);
    }

    @Override
    public long count() {
        return users.size();
    }
}
