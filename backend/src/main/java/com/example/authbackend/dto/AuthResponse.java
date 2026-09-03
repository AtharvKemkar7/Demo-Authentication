package com.example.authbackend.dto;

public record AuthResponse(String token, String username, String role) {
}
