package com.example.authbackend.controller;

import com.example.authbackend.dto.DashboardData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/data")
    public Map<String, Object> getDashboardData(Authentication authentication) {
        String username = extractUsername(authentication);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        DashboardData data = new DashboardData(
                "Welcome! This is protected data only accessible with a valid JWT.",
                username,
                LocalDateTime.now().format(formatter)
        );

        return Map.of(
                "message", data.message(),
                "user", data.user(),
                "serverTime", data.serverTime(),
                "stats", List.of(
                        Map.of("label", "Total Users", "value", "12,847"),
                        Map.of("label", "Active Sessions", "value", "1,024"),
                        Map.of("label", "API Requests", "value", "89,312"),
                        Map.of("label", "Success Rate", "value", "99.2%")
                )
        );
    }

    private String extractUsername(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return "anonymous";
        }
        return userDetails.getUsername();
    }
}
