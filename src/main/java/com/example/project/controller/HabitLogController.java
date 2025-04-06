package com.example.project.controller;

import com.example.project.model.dto.request.HabitLogRequest;
import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.HabitLog;
import com.example.project.service.AppUserService;
import com.example.project.service.HabitLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/habit-logs")
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {

    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<List<HabitLog>>> getAllHabitLogsByHabitId(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable("habit-id") UUID id) {
        List<HabitLog> habitLogs = habitLogService.getAllHabitLogsByHabitId(page, size, id);

        ApiResponse<List<HabitLog>> response = ApiResponse.<List<HabitLog>>builder()
                .success(true)
                .message("Get all habit logs successfully")
                .status(HttpStatus.OK)
                .payload(habitLogs)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HabitLog>> createHabitLog(@RequestBody HabitLogRequest habitLogRequest) {
        HabitLog habitLog = habitLogService.createHabitLog(habitLogRequest);

        ApiResponse<HabitLog> response = ApiResponse.<HabitLog>builder()
                .success(true)
                .message("Create habit log successfully")
                .status(HttpStatus.OK)
                .payload(habitLog)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
