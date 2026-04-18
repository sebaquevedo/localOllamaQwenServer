package com.sebacirk.ai_backend.controller;

import com.sebacirk.ai_backend.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/chat")
    @PreAuthorize("hasAnyRole('USER', 'PREMIUM', 'ADMIN')")
    public ResponseEntity<String> chat(@RequestBody String prompt) {
        return ResponseEntity.ok(aiService.chat(prompt));
    }

    @PostMapping("/chat/premium")
    @PreAuthorize("hasAnyRole('PREMIUM', 'ADMIN')")
    public ResponseEntity<String> chatPremium(@RequestBody String prompt) {
        return ResponseEntity.ok(aiService.chatPremium(prompt));
    }
}