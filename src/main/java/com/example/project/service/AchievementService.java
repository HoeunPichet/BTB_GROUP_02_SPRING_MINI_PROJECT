package com.example.project.service;

import com.example.project.model.entity.Achievement;

import java.util.List;
import java.util.UUID;

public interface AchievementService {
    List<Achievement> getAllAchievement(Integer page, Integer size);

    List<Achievement> getAchievementByAppUser(UUID userId, Integer page, Integer size);
}
