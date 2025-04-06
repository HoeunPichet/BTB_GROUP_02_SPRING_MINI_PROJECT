package com.example.project.service.impl;

import com.example.project.model.dto.request.ProfileRequest;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.repository.AppUserRepository;
import com.example.project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public AppUserRegister getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());

        AppUser appUser = appUserRepository.getUserById(userId);
        AppUserRegister appUserResponse = mapper.map(appUser, AppUserRegister.class);
        appUserResponse.setUsername(appUser.getName());

        return appUserResponse;
    }

    @Override
    public AppUserRegister updateUserProfile(ProfileRequest profileRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());

        AppUser appUser = appUserRepository.updateUserProfile(userId, profileRequest);
        AppUserRegister appUserResponse = mapper.map(appUser, AppUserRegister.class);
        appUserResponse.setUsername(appUser.getName());

        return appUserResponse;
    }

    @Override
    public void deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) auth.getCredentials());

        appUserRepository.deleteUser(userId);
    }
}
