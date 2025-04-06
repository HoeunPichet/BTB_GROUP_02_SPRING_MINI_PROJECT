package com.example.project.service.impl;

import com.example.project.model.dto.request.HabitLogRequest;
import com.example.project.model.entity.HabitLog;
import com.example.project.repository.HabitLogRepository;
import com.example.project.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HabitLogServiceImp implements HabitLogService {

    private final HabitLogRepository habitLogRepository;

    public HabitLogServiceImp(HabitLogRepository habitLogRepository) {
        this.habitLogRepository = habitLogRepository;
    }

    @Override
    public List<HabitLog> getAllHabitLogsByHabitId(Integer page, Integer size, UUID id) {
        return habitLogRepository.getAllHabitLogsByHabitId(page, size, id);
    }

    @Override
    public HabitLog createHabitLog(HabitLogRequest habitLogRequest) {

        HabitLog habitLog = habitLogRepository.createHabitLog(habitLogRequest);
        return habitLog;
    }
}
