package com.sebacirk.ai_backend.controller;

import com.sebacirk.ai_backend.entity.AppConfig;
import com.sebacirk.ai_backend.service.AppConfigService;
import com.sebacirk.ai_backend.entity.Role;
import com.sebacirk.ai_backend.entity.User;
import com.sebacirk.ai_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppConfigService appConfigService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id,
                                               @RequestParam String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.valueOf(role.toUpperCase()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long id,
                                                     @RequestParam String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok("Password updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/config/limit")
    public ResponseEntity<Integer> getUserDailyLimit() {
        return ResponseEntity.ok(appConfigService.getUserDailyLimit());
    }

    @PutMapping("/config/limit")
    public ResponseEntity<AppConfig> updateUserDailyLimit(@RequestParam int limit) {
        return ResponseEntity.ok(appConfigService.updateUserDailyLimit(limit));
    }
}