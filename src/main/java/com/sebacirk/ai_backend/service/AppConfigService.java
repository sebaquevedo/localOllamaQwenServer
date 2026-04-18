package com.sebacirk.ai_backend.service;

import com.sebacirk.ai_backend.entity.AppConfig;
import com.sebacirk.ai_backend.repository.AppConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppConfigService {

    private final AppConfigRepository appConfigRepository;

    private static final String USER_DAILY_LIMIT_KEY = "user_daily_limit";
    private static final int DEFAULT_DAILY_LIMIT = 10;

    public int getUserDailyLimit() {
        return appConfigRepository.findById(USER_DAILY_LIMIT_KEY)
                .map(config -> Integer.parseInt(config.getConfigValue()))
                .orElse(DEFAULT_DAILY_LIMIT);
    }

    public AppConfig updateUserDailyLimit(int limit) {
        AppConfig config = AppConfig.builder()
                .configKey(USER_DAILY_LIMIT_KEY)
                .configValue(String.valueOf(limit))
                .build();
        return appConfigRepository.save(config);
    }
}