package com.example.project.service;

import com.example.project.model.dto.request.HabitRequest;
import com.example.project.model.entity.Habit;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    Habit createHabit(HabitRequest habit, UUID userId);
    List<Habit> getAllHabit(Integer page,Integer size,UUID userId);
    Habit getHabitById(UUID id);
    Habit deleteHabit(UUID id);
    Habit updateHabitById(UUID id, HabitRequest habit);

}
