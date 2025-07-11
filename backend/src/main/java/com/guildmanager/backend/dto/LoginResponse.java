package com.guildmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private Long userId;
    private String role;
    private String username;
    private Long guildId;
    private String guildName;
}
