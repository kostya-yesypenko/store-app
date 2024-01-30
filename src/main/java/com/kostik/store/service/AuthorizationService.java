package com.kostik.store.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {


    public boolean validatePasswords(String password1, String password2) {
    	return Objects.equals(password1, password2);
//        return passwordEncoder.matches(password1, password2);
    }
}
