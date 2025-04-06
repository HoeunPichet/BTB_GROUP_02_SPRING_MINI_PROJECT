package com.example.project.service;

import com.example.project.model.dto.request.ProfileRequest;
import com.example.project.model.entity.AppUserRegister;
import jakarta.validation.Valid;

public interface ProfileService {
    AppUserRegister getUserProfile();

    AppUserRegister updateUserProfile(@Valid ProfileRequest profileRequest);

    void deleteUser();
}
