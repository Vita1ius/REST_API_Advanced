package com.epam.esm.REST_API_Advanced.repository;

import com.epam.esm.REST_API_Advanced.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
}
