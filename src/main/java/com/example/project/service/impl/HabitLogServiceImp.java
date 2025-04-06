package com.example.project.service.impl;

import com.example.project.exception.AppNotFoundException;
import com.example.project.model.dto.request.HabitLogRequest;
import com.example.project.model.entity.Achievement;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.HabitLog;
import com.example.project.repository.AchievementRepository;
import com.example.project.repository.AppUserRepository;
import com.example.project.repository.HabitLogRepository;
import com.example.project.service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitLogServiceImp implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final AppUserRepository appUserRepository;
    private final AchievementRepository achievementRepository;

    @Override
    public List<HabitLog> getAllHabitLogsByHabitId(Integer page, Integer size, UUID id) {
        List<HabitLog> habitLog = habitLogRepository.getAllHabitLogsByHabitId(page, size, id);

        if (habitLog.isEmpty()) {
            throw new AppNotFoundException("Habit ID is not found.");
        }

        return habitLog;
    }

    @Override
    public HabitLog createHabitLog(HabitLogRequest habitLogRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());

        AppUser appUser = appUserRepository.updateUserXpById(userId);
        HabitLog habitLog = habitLogRepository.createHabitLog(habitLogRequest);
        List<Achievement> achievement = achievementRepository.findAchievementxByXp(appUser.getXp());

        if (!achievement.isEmpty()) {
            achievementRepository.deleteUserAchievement(userId);
            for (Achievement achieve : achievement) {
                achievementRepository.addUserAchievement(userId, achieve.getAchievementId());
            }
        }

        return habitLog;
    }
}
