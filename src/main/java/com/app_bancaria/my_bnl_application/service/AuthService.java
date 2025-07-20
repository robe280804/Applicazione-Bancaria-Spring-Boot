package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.dto.AuthRequestDto;
import com.app_bancaria.my_bnl_application.dto.AuthResponseDto;
import com.app_bancaria.my_bnl_application.exception.EmailAlreadyExistsEx;
import com.app_bancaria.my_bnl_application.model.Role;
import com.app_bancaria.my_bnl_application.model.User;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import com.app_bancaria.my_bnl_application.security.jwt.JwtService;
import com.app_bancaria.my_bnl_application.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDto register(AuthRequestDto request) {
        log.info("[REGISTER] Registrazione in esecuzione da {}", request.getEmail());

        Optional<User> registerUser = userRepository.findByEmail(request.getEmail());
        if (registerUser.isPresent()){
            throw new EmailAlreadyExistsEx("Email già registrata nel sistema, Riprova");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER))
                .build();

        User savedUser =  userRepository.save(user);
        return AuthResponseDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .message("Registrazione avvenuta con successo")
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Transactional
    public AuthResponseDto login(AuthRequestDto request) {
        log.info("[LOGIN] Login in esecuzione da {}", request.getEmail());
        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            log.warn("[LOGIN] Fallito per email {}: {}", request.getEmail(), ex.getMessage());
            throw ex;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(
                userPrincipal.getId(), userPrincipal.getEmail(), userPrincipal.getAuthorities());

        return AuthResponseDto.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .message("Login andato a buon fine")
                .token(token)
                .build();
    }
}
