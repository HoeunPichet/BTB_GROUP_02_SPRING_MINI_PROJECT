package com.example.project.service.impl;

import com.example.project.exception.AppNotFoundException;
import com.example.project.model.dto.request.HabitRequest;
import com.example.project.model.entity.Habit;
import com.example.project.repository.HabitRepository;
import com.example.project.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    @Override
    public Habit createHabit(HabitRequest habit, UUID userId) {
        return habitRepository.createHabit(habit,userId);
    }

    @Override
    public List<Habit> getAllHabit(Integer page,Integer size,UUID userId) {
        List<Habit> habits = habitRepository.getAllHabit(page,size,userId);
        if(habits.isEmpty()){
            throw new AppNotFoundException("Habit don't have any data yet !!");
        }
        return habitRepository.getAllHabit(page,size,userId);
    }

    @Override
    public Habit getHabitById(UUID id) {
        Habit habits = habitRepository.getHabitById(id);
        if (habits == null) {
            throw new AppNotFoundException("Habit ID= "+id+" not found");
        }
        return habits;
    }

    @Override
    public Habit deleteHabit(UUID id) {
        Habit habits = habitRepository.deleteHabit(id);
        if (habits == null) {
            throw new AppNotFoundException("Habit ID= "+id+" not found");
        }
        return habits;
    }

    @Override
    public Habit updateHabitById(UUID id, HabitRequest habit) {
        Habit habits = habitRepository.updateHabitById(id,habit);
        if (habits == null) {
            throw new AppNotFoundException("Habit ID= "+id+" not found");
        }
        return habits;
    }

}
