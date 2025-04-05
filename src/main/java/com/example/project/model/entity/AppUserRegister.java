package com.example.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegister {
    private Integer appUserId;
    private String username;
    private String email;
    private Integer level;
    private Integer xp;
    private String profileImageUrl;
    private Boolean isVerified;
    private LocalDateTime createdAt;
}
