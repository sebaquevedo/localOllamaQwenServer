package com.sebacirk.ai_backend.repository;

import com.sebacirk.ai_backend.entity.QueryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {
    long countByUsernameAndCreatedAtAfter(String username, LocalDateTime date);
}