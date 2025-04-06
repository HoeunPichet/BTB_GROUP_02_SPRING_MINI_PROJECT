package com.example.project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters")
    @Size(max = 50, message = "Username cannot be greater than 50 characters")
    private String username;

    @Size(max = 255, message = "Image Url cannot be greater than 255 characters")
    private String profileImageUrl;
}
