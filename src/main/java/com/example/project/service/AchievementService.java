package com.example.project.service;

import com.example.project.model.entity.Achievement;

import java.util.List;

public interface AchievementService {
    List<Achievement> getAllAchievement(Integer page, Integer size);
}
