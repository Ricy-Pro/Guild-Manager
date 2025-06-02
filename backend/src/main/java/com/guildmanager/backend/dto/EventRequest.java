package com.guildmanager.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private Integer attendanceLimit;
    private Long guildId;
}
