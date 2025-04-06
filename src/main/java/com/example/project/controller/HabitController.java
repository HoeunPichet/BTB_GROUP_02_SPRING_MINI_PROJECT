package com.example.project.controller;

import com.example.project.model.dto.request.HabitRequest;
import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.Achievement;
import com.example.project.model.entity.Habit;
import com.example.project.service.HabitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/habit")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {
    private final HabitService habitService;
    @PostMapping
    public ResponseEntity<ApiResponse<Habit>> createHabit(@Valid @RequestBody HabitRequest habit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());
        Habit habits=habitService.createHabit(habit,userId);

        ApiResponse<Habit> response = ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit created successfully!")
                .status(HttpStatus.CREATED)
                .payload(habits)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Habit>>> getAllHabit(@RequestParam(defaultValue = "1") @Min(value = 1, message = "must be greater than 0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());
        List<Habit> habits=habitService.getAllHabit(page,size,userId);
        ApiResponse<List<Habit>> response=ApiResponse.<List<Habit>>builder()
                .success(true)
                .message("Habit retrieved successfully")
                .status(HttpStatus.OK)
                .payload(habits)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Habit>> getAllHabitById( @PathVariable UUID id) {
        Habit habits=habitService.getHabitById(id);
        ApiResponse<Habit> response= ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit ID= " +id+" was founded successfully")
                .status(HttpStatus.OK)
                .payload(habits)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Habit>> deleteHabit(@PathVariable UUID id) {
        Habit habits=habitService.deleteHabit(id);
        ApiResponse<Habit> response= ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit ID= "+id+" deleted successfully")
                .status(HttpStatus.OK)
                .payload(habits)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Habit>> updateHabit(@PathVariable UUID id, @Valid @RequestBody HabitRequest habit) {
        Habit habits=habitService.updateHabitById(id,habit);
        ApiResponse<Habit> response= ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit ID= "+id+" updated successfully")
                .status(HttpStatus.OK)
                .payload(habits)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
