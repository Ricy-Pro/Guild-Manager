// src/main/java/com/guildmanager/backend/repository/GuildRepository.java
package com.guildmanager.backend.repository;

import com.guildmanager.backend.model.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildRepository extends JpaRepository<Guild, Long> {
    Optional<Guild> findByName(String name);
}
