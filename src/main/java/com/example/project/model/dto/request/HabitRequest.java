package com.example.project.model.dto.request;

import com.example.project.exception.HabitFrequencyDeserializer;
import com.example.project.model.enums.HabitFrequency;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class HabitRequest {
    @NotBlank(message = "Tittle is required")
    private String habitTitle;
    private String description;
    @JsonDeserialize(using = HabitFrequencyDeserializer.class)
    private HabitFrequency frequency;
}
