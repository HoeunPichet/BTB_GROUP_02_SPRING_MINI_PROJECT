package com.example.project.model.entity;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Achievement {
    private Integer achievementId;
    private String title;
    private String description;
    private String badge;
    private Integer xpRequired;
}
