package com.guildmanager.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private Integer attendanceLimit;
    private SimpleGuild guild;
    private SimpleUser createdBy;
    private Set<SimpleUser> participants;

    @Data
    @Builder
    public static class SimpleGuild {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    public static class SimpleUser {
        private Long id;
        private String username;
        private String role;
    }
}
