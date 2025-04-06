package com.example.project.controller;


import com.example.project.model.dto.request.ProfileRequest;
import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    ResponseEntity<ApiResponse<AppUserRegister>> getUserProfile() {
        AppUserRegister appUser = profileService.getUserProfile();
        ApiResponse<AppUserRegister> response = ApiResponse.<AppUserRegister>builder()
                .success(true)
                .message("User profile fetched successfully!")
                .payload(appUser)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    ResponseEntity<ApiResponse<AppUserRegister>> updateAppUserRegister(@RequestBody @Valid ProfileRequest profileRequest) {
        AppUserRegister appUser = profileService.updateUserProfile(profileRequest);
        ApiResponse<AppUserRegister> response = ApiResponse.<AppUserRegister>builder()
                .success(true)
                .message("User Profile has been updated successfully!")
                .payload(appUser)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    ResponseEntity<ApiResponse<?>> deleteUser() {
        profileService.deleteUser();
        ApiResponse<?> response = ApiResponse.builder()
                .success(true)
                .message("User has been deleted successfully!")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
}
