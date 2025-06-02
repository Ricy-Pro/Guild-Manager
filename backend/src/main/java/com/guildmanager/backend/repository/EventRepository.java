package com.guildmanager.backend.repository;


import com.guildmanager.backend.model.Event;
import com.guildmanager.backend.model.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByGuild(Guild guild);
}
