package com.sebacirk.ai_backend.service;

import com.sebacirk.ai_backend.entity.QueryLog;
import com.sebacirk.ai_backend.entity.Role;
import com.sebacirk.ai_backend.entity.User;
import com.sebacirk.ai_backend.repository.QueryLogRepository;
import com.sebacirk.ai_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final QueryLogRepository queryLogRepository;
    private final UserRepository userRepository;
    private final AppConfigService appConfigService;

    public String chat(String prompt) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.USER) {
            int dailyLimit = appConfigService.getUserDailyLimit();

            long todayQueries = queryLogRepository.countByUsernameAndCreatedAtAfter(
                    username,
                    LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
            );

            if (todayQueries >= dailyLimit) {
                throw new RuntimeException("Daily limit of " + dailyLimit + " queries reached");
            }
        }

        queryLogRepository.save(QueryLog.builder()
                .username(username)
                .createdAt(LocalDateTime.now())
                .build());

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String chatPremium(String prompt) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        queryLogRepository.save(QueryLog.builder()
                .username(username)
                .createdAt(LocalDateTime.now())
                .build());

        return chatClient.prompt()
                .system("Eres un asistente avanzado con capacidades extendidas.")
                .user(prompt)
                .call()
                .content();
    }
}