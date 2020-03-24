package de.stw.phoenix.user.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

//    Optional<User> find(String userName);
//
//    User save(User newUser);
//
//    long count();
//
//    List<User> findAll();
//
}
