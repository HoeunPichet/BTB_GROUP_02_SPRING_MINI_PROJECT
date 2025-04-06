package com.example.project.service;

import com.example.project.model.dto.request.HabitLogRequest;
import com.example.project.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    List<HabitLog> getAllHabitLogsByHabitId(Integer page, Integer size, UUID id);
    HabitLog createHabitLog(HabitLogRequest habitLogRequest);
}
