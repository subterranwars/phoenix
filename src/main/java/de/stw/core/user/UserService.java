package de.stw.core.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.stw.rest.UserCreateRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {
    private List<User> users;
    private Map<String, User> tokens = Maps.newHashMap();

    @PostConstruct
    public void init() {
        this.users = Lists.newArrayList(
            User.builder(1, "marskuh").password("password").withDefaults().build(),
            User.builder(2, "fafner").password("password").withDefaults().build()
        );
        this.users.get(0).getResources().forEach(rp -> rp.store(1000));
    }

    public void save(User user) {
        Objects.requireNonNull(user);
        this.users.add(user);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public Optional<User> find(int userId) {
        return users.stream().filter(u -> u.getId() == userId).findAny();
    }

    public Optional<User> find(String userName) {
        return users.stream().filter(u -> Objects.equals(u.getName(), userName)).findAny();
    }

    // TODO MVR implement properly
    public String authenticate(String userName, String password) {
        final Optional<User> userOptional = find(userName);
        if (userOptional.isPresent() && password.equals(userOptional.get().getPassword())) {
            final String token = UUID.randomUUID().toString();
            tokens.put(token, userOptional.get());
            return token;
        }
        throw new BadCredentialsException("Username or password incorrect");
    }

    public void create(UserCreateRequest userCreateRequest) {
        if (find(userCreateRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        final User newUser = User.builder(users.size() + 1, userCreateRequest.getUsername())
                .password(userCreateRequest.getPassword())
                .withDefaults().build();
        save(newUser);
    }

    public Optional<User> findByToken(String token) {
        Optional<User> authenticatedUser = Optional.ofNullable(tokens.get(token));
        return authenticatedUser;
    }
}
