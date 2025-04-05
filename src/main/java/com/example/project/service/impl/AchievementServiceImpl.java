package com.example.project.service.impl;

import com.example.project.model.entity.Achievement;
import com.example.project.repository.AchievementRepository;
import com.example.project.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;

    @Override
    public List<Achievement> getAllAchievement(Integer page, Integer size) {
        return achievementRepository.findAllAchievement(page, size);
    }
}
