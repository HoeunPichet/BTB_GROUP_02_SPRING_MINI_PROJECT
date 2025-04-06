package com.example.project.service.impl;

import com.example.project.model.entity.Achievement;
import com.example.project.repository.AchievementRepository;
import com.example.project.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;

    @Override
    public List<Achievement> getAllAchievement(Integer page, Integer size) {
        return achievementRepository.findAllAchievement(page, size);
    }

    @Override
    public List<Achievement> getAchievementByAppUser(UUID userId, Integer page, Integer size) {
        return achievementRepository.findAchievementByAppUser(userId, page, size);
    }

}
