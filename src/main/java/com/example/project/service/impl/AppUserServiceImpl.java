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
        return appUserRepository.getUserByIdentifier(identifier);
    }

    @Override
    public AppUserRegister registerUser(@Valid RegisterRequest registerRequest) {
        AppUser findUser = appUserRepository.getUserByIdentifier(registerRequest.getEmail());

        if (findUser != null) {
            throw new ThrowFieldException("email", "Email has already taken");
        }

        String encodedPass = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPass);
        AppUser appUser = appUserRepository.registerUser(registerRequest);
        AppUserRegister appUserResponse = mapper.map(appUserRepository.getUserById(appUser.getAppUserId()), AppUserRegister.class);
        appUserResponse.setUsername(appUser.getName());

        return appUserResponse;
    }

    @Override
    public AppUserRegister findUserByIdentifier(String email, String password) {
        AppUser appUser = appUserRepository.getUserByIdentifier(email);
        if (appUser == null) throw new AppBadRequestException("Invalid username, email, or password. Please check your credentials and try again.");

        boolean isCorrect = passwordEncoder.matches(password, appUser.getPassword());
        if (!isCorrect) throw new AppBadRequestException("Invalid username, email, or password. Please check your credentials and try again.");

        if (!appUser.getIsVerified()) throw new AppBadRequestException("User has not verified yet.");

        AppUserRegister appUserResponse = mapper.map(appUser, AppUserRegister.class);
        appUserResponse.setUsername(appUser.getName());

        return appUserResponse;
    }

    @Override
    public void checkEmailBeforeOpt(String email) {
        AppUser appUser = appUserRepository.getUserByEmail(email);
        if (appUser == null) {
            throw new AppBadRequestException("Your email has not registered yet.");
        }

        if (appUser.getIsVerified()) {
            throw new AppBadRequestException("Your email has already verified.");
        }
    }

    @Override
    public void verifyEmailWithOpt(String email) {
        appUserRepository.verifyEmailWithOpt(email);
    }

}
