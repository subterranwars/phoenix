package de.stw.core.user;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private List<User> users;

    @PostConstruct
    public void init() {
        this.users = Lists.newArrayList(
            User.builder(1, "marskuh").withDefaults().build(),
            User.builder(2, "fafner").withDefaults().build()
        );
        this.users.get(0).getResources().forEach(rp -> rp.store(1000));
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
