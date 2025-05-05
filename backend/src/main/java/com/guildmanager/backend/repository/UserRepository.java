// src/main/java/com/guildmanager/backend/repository/UserRepository.java
package com.guildmanager.backend.repository;

import com.guildmanager.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
