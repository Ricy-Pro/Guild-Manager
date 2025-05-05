package com.guildmanager.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String guildName;
    private boolean asLeader;
}
