package com.guildmanager.backend.service.controller;

import com.guildmanager.backend.dto.EventRequest;
import com.guildmanager.backend.dto.EventResponse;
import com.guildmanager.backend.model.Event;
import com.guildmanager.backend.model.Guild;
import com.guildmanager.backend.model.User;
import com.guildmanager.backend.service.EventNotificationService;
import com.guildmanager.backend.service.EventService;
import com.guildmanager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final EventNotificationService notificationService;

    private Event toEntity(EventRequest req) {
        Event event = new Event();
        event.setName(req.getName());
        event.setDescription(req.getDescription());
        event.setDateTime(req.getDateTime());
        event.setAttendanceLimit(req.getAttendanceLimit());
        Guild guild = new Guild();
        guild.setId(req.getGuildId());
        event.setGuild(guild);
        return event;
    }

    private EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .dateTime(event.getDateTime())
                .attendanceLimit(event.getAttendanceLimit())
                .guild(EventResponse.SimpleGuild.builder()
                        .id(event.getGuild().getId())
                        .name(event.getGuild().getName())
                        .build())
                .createdBy(EventResponse.SimpleUser.builder()
                        .id(event.getCreatedBy().getId())
                        .username(event.getCreatedBy().getUsername())
                        .role(event.getCreatedBy().getRole().name())
                        .build())
                .participants(event.getParticipants().stream()
                        .map(u -> EventResponse.SimpleUser.builder()
                                .id(u.getId())
                                .username(u.getUsername())
                                .role(u.getRole().name())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @PostMapping
    public EventResponse create(@RequestBody EventRequest eventRequest, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        Event event = eventService.createEvent(toEntity(eventRequest), user);
        EventResponse response = toResponse(event);
        notificationService.notifyChange("create", response);
        return response;
    }


    @GetMapping("/guild/{guildId}")
    public List<EventResponse> listByGuild(@PathVariable Long guildId) {
        Guild guild = new Guild();
        guild.setId(guildId);
        List<Event> events = eventService.getEventsForGuild(guild);
        return events.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id, @RequestBody EventRequest eventRequest, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        Event eventToUpdate = toEntity(eventRequest);
        Event updatedEvent = eventService.updateEvent(id, eventToUpdate, user);
        EventResponse response = toResponse(updatedEvent);
        notificationService.notifyChange("update", response);
        return response;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        eventService.deleteEvent(id, user);
        notificationService.notifyChange("delete", id);
    }


    @PostMapping("/{id}/join")
    public EventResponse join(@PathVariable Long id, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        Event event = eventService.joinEvent(id, user);
        EventResponse response = toResponse(event);
        notificationService.notifyChange("join", response);
        return response;
    }


    @PostMapping("/{id}/exit")
    public EventResponse exit(@PathVariable Long id, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        Event event = eventService.exitEvent(id, user);
        EventResponse response = toResponse(event);
        notificationService.notifyChange("exit", response);
        return response;
    }

}
