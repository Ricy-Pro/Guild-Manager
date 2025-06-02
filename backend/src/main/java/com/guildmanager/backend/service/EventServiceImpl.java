package com.guildmanager.backend.service;

import com.guildmanager.backend.model.Event;
import com.guildmanager.backend.model.Guild;
import com.guildmanager.backend.model.User;
import com.guildmanager.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(Event event, User creator) {
        event.setCreatedBy(creator);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsForGuild(Guild guild) {
        return eventRepository.findByGuild(guild);
    }

    @Override
    public Event updateEvent(Long id, Event updated, User user) {
        Event event = eventRepository.findById(id).orElseThrow();
        if (!event.getGuild().equals(user.getGuild()))
            throw new RuntimeException("Unauthorized");
        event.setName(updated.getName());
        event.setDescription(updated.getDescription());
        event.setDateTime(updated.getDateTime());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id, User user) {
        Event event = eventRepository.findById(id).orElseThrow();
        if (!event.getCreatedBy().getId().equals(user.getId()))
            throw new RuntimeException("Only creator can delete");
        eventRepository.delete(event);
    }

    @Override
    public Event joinEvent(Long id, User user) {
        Event event = eventRepository.findById(id).orElseThrow();
        if (event.getAttendanceLimit() != null &&
            event.getParticipants().size() >= event.getAttendanceLimit())
            throw new RuntimeException("Limit reached");
        event.getParticipants().add(user);
        return eventRepository.save(event);
    }

    @Override
    public Event exitEvent(Long id, User user) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.getParticipants().remove(user);
        return eventRepository.save(event);
    }
}
