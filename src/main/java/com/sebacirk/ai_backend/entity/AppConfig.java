package com.sebacirk.ai_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppConfig {

    @Id
    @Column(nullable = false, unique = true)
    private String configKey;

    @Column(nullable = false)
    private String configValue;
}