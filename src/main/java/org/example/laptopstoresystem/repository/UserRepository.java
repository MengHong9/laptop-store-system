package org.example.laptopstoresystem.repository;

import org.example.laptopstoresystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);

    List<User> findByNameContainsIgnoreCase(String name);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);


}
