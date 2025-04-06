package com.example.project.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.project.model.enums.HabitFrequency;

import java.io.IOException;

public class HabitFrequencyDeserializer extends JsonDeserializer<HabitFrequency> {

    @Override
    public HabitFrequency deserialize(JsonParser p, DeserializationContext text) throws IOException {
        String frequency = p.getText().trim().toUpperCase();
        try {
            return HabitFrequency.valueOf(frequency);
        } catch (IllegalArgumentException e) {
            throw new HandleEnumHabitFrequencyException("Invalid frequency value: " + frequency + ". Allowed values are: DAILY, WEEKLY, MONTHLY.");
        }
    }
}
