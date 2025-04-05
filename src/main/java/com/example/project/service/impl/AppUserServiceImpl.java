package com.example.project.service.impl;

import com.example.project.exception.ThrowFieldException;
import com.example.project.model.dto.request.RegisterRequest;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.repository.AppUserRepository;
import com.example.project.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.getUserByIdentifier(identifier);

        if (appUser == null) {
            throw new ThrowFieldException("identifier", "Identifier is not existing");
        }

        if (!appUser.getIsVerified()) {
            throw new ThrowFieldException("user", "User has not verified yet");
        }

        return appUser;
    }

    @Override
    public AppUserRegister registerUser(@Valid RegisterRequest registerRequest) {
        String encodedPass = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPass);
        AppUser appUser = appUserRepository.registerUser(registerRequest);
        appUser.setEmail(appUser.getUsername());
        appUser.setUsername(appUser.getName());

        return mapper.map(appUserRepository.getUserById(appUser.getAppUserId()), AppUserRegister.class);
    }
}
