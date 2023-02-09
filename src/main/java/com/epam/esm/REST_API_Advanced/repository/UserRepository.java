package com.epam.esm.REST_API_Advanced.repository;

import com.epam.esm.REST_API_Advanced.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);
    boolean existsByName(String name);
}
