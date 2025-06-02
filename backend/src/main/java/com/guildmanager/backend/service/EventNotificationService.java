package com.guildmanager.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public EventNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyChange(String action, Object eventData) {
        messagingTemplate.convertAndSend("/topic/events", new EventMessage(action, eventData));
    }

    public record EventMessage(String action, Object eventData) {}
}
