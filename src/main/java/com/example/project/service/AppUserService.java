package com.example.project.service;

import com.example.project.model.dto.request.RegisterRequest;
import com.example.project.model.entity.AppUserRegister;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    AppUserRegister registerUser(@Valid RegisterRequest appUserRequest);

    void findUserByIdentifier (String email, String password);
}
