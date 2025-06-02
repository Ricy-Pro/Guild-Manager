package com.guildmanager.backend.service.controller;

import com.guildmanager.backend.dto.AuthResponse;
import com.guildmanager.backend.dto.LoginRequest;
import com.guildmanager.backend.dto.LoginResponse;
import com.guildmanager.backend.dto.RegisterRequest;
import com.guildmanager.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
