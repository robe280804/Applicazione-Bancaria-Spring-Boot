package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.model.User;
import com.app_bancaria.my_bnl_application.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder;

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getId();
    }

    public Boolean verifyPassword(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }
}
