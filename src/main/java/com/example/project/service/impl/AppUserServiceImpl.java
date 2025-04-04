package com.example.project.service.impl;

import com.example.project.exception.ThrowFieldException;
import com.example.project.model.entity.AppUser;
import com.example.project.repository.AppUserRepository;
import com.example.project.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.getUserByIdentifier(identifier);

        if (appUser == null) {
            throw new ThrowFieldException("identifier", "Identifier is not existing");
        }

        return appUser;
    }
}
