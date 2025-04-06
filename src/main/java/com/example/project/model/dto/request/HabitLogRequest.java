package com.example.project.model.dto.request;

import com.example.project.model.enums.HabitStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitLogRequest {
    @NotBlank(message = "Status is required")
    private HabitStatus status;

    @NotBlank(message = "Habit id is required")
    private UUID habitId;
}
