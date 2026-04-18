package com.sebacirk.ai_backend.repository;

import com.sebacirk.ai_backend.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppConfigRepository extends JpaRepository<AppConfig, String> {
}