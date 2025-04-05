package com.example.project.controller;

import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.Achievement;
import com.example.project.service.AchievementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievements")
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Achievement>>> getAchievements(@RequestParam(defaultValue = "1") @Min(value = 1, message = "must be greater than 0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<Achievement> achievements = achievementService.getAllAchievement(page, size);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) auth.getCredentials();

        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements retrieved successfully!")
                .payload(achievements)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
