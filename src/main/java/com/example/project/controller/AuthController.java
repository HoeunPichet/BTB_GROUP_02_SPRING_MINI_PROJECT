package com.example.project.controller;

import com.example.project.exception.AppNotFoundException;
import com.example.project.exception.ThrowFieldException;
import com.example.project.jwt.JwtUtils;
import com.example.project.model.dto.request.LoginRequest;
import com.example.project.model.dto.request.RegisterRequest;
import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.model.entity.LoginToken;
import com.example.project.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginToken>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String identifier = loginRequest.getIdentifier();
        String password = loginRequest.getPassword();

        AppUserRegister appUserRegister = appUserService.findUserByIdentifier(identifier, password);

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(appUserRegister.getEmail(), password)
        );

        if (!auth.isAuthenticated()) {
            throw new AppNotFoundException("Log in failed");
        }
        ApiResponse<LoginToken> response = ApiResponse.<LoginToken>builder()
                .success(true)
                .message("Logged in successfully")
                .status(HttpStatus.OK)
                .payload(new LoginToken(jwtUtils.generateToken(identifier, appUserRegister.getAppUserId())))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AppUserRegister>> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {

        AppUserRegister appUser = appUserService.registerUser(registerRequest);

        ApiResponse<AppUserRegister> response = ApiResponse.<AppUserRegister>builder()
                .success(true)
                .message("Registered successfully")
                .status(HttpStatus.CREATED)
                .payload(appUser)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
