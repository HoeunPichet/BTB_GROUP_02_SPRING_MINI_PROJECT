package com.example.project.service.impl;

import com.example.project.exception.AppBadRequestException;
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
        return appUserRepository.getUserByEmail(identifier);
    }

    @Override
    public AppUserRegister registerUser(@Valid RegisterRequest registerRequest) {
        AppUser findUser = appUserRepository.getUserByEmail(registerRequest.getEmail());

        if (findUser != null) {
            throw new ThrowFieldException("email", "Email has already taken");
        }

        String encodedPass = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPass);
        AppUser appUser = appUserRepository.registerUser(registerRequest);

        return mapper.map(appUserRepository.getUserById(appUser.getAppUserId()), AppUserRegister.class);
    }

    @Override
    public void findUserByIdentifier(String email, String password) {
        AppUser appUser = appUserRepository.getUserByEmail(email);
        if (appUser == null) throw new AppBadRequestException("Invalid username, email, or password. Please check your credentials and try again.");

        boolean isCorrect = passwordEncoder.matches(password, appUser.getPassword());
        if (!isCorrect) throw new AppBadRequestException("Invalid username, email, or password. Please check your credentials and try again.");

        if (!appUser.getIsVerified()) throw new AppBadRequestException("User has not verified yet");
    }
}
