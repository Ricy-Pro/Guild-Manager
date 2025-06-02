package com.guildmanager.backend.service;



import com.guildmanager.backend.model.Event;
import com.guildmanager.backend.model.Guild;
import com.guildmanager.backend.model.User;

import java.util.List;

public interface EventService {
    Event createEvent(Event event, User creator);
    List<Event> getEventsForGuild(Guild guild);
    Event updateEvent(Long id, Event updatedEvent, User user);
    void deleteEvent(Long id, User user);
    Event joinEvent(Long id, User user);
    Event exitEvent(Long id, User user);
}
