// src/main/java/com/guildmanager/backend/service/UserService.java
package com.guildmanager.backend.service;

import com.guildmanager.backend.model.User;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
}
