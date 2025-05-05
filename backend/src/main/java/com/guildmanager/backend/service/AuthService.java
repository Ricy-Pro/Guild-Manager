package com.guildmanager.backend.service;

import com.guildmanager.backend.dto.*;
import com.guildmanager.backend.exceptions.InvalidCredentialsException;
import com.guildmanager.backend.model.*;
import com.guildmanager.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GuildRepository guildRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("Username already exists");
        }

        if (request.isAsLeader()) {
            if (guildRepository.findByName(request.getGuildName()).isPresent()) {
                return new AuthResponse("Guild name already taken");
            }

            User leader = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(UserRole.GUILD_LEADER)
                    .build();

            userRepository.save(leader);

            Guild guild = Guild.builder()
                    .name(request.getGuildName())
                    .leader(leader)
                    .build();

            guildRepository.save(guild);

            leader.setGuild(guild);
            userRepository.save(leader);

            return new AuthResponse("Guild Leader registered successfully");

        } else {
            Guild guild = guildRepository.findByName(request.getGuildName())
                    .orElseThrow(() -> new RuntimeException("Guild not found"));

            User member = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(UserRole.GUILD_MEMBER)
                    .guild(guild)
                    .build();

            userRepository.save(member);
            return new AuthResponse("Guild Member registered successfully");
        }
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }


        return new LoginResponse("Login successful",user.getId(),user.getRole().name());
    }
}
