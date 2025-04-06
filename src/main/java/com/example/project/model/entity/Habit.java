package com.example.project.model.entity;

import com.example.project.model.enums.HabitFrequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    private UUID habitId;
    private String habitTitle;
    private String description;
    private HabitFrequency frequency;
    private boolean is_active;
    private AppUserRegister appUser;
    private LocalDateTime created_at;
}
