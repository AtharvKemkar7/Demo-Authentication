package com.example.authbackend.config;

import com.example.authbackend.entity.User;
import com.example.authbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "ADMIN"));
            }
            if (!userRepository.existsByUsername("user")) {
                userRepository.save(new User("user", passwordEncoder.encode("user123"), "USER"));
            }
        };
    }
}
