package de.stw.phoenix.user.api;

import de.stw.phoenix.user.rest.UserCreateRequest;

public interface UserService {
    void create(UserCreateRequest userCreateRequest);

    User save(User user);
}
