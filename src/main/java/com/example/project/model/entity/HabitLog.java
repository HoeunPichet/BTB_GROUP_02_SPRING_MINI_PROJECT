package com.example.project.model.entity;

import com.example.project.model.enums.HabitStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitLog {
    private UUID habitLogId;
    private String logDate;
    private HabitStatus status;
    private Integer xpEarned;
    private UUID habitId;
}
