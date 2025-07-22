package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.dto.UserResponseDto;
import com.app_bancaria.my_bnl_application.dto.UserUpdateRequestDto;
import com.app_bancaria.my_bnl_application.model.User;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        log.info("[ADMIN] Visualizzazione di tutti gli user");

        return userRepository.findAll().stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRoles())
                        .createdAt(user.getCreatedAt())
                        .updateAt(user.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(String id) {
        log.info("[ADMIN] Tentativo eliminazione user con id {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));

        userRepository.delete(user);
        log.info("[ADMIN] Eliminazione di {} avvenuta con successo", id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDto update(String id, UserUpdateRequestDto request) {
        log.info("[ADMIN] Aggiornamento user con id {}, nuovi dati email={} e password={}",
                id, request.getEmail(), request.getPassword());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));

        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(request.getRole());

        User userSalvato = userRepository.save(user);

        return UserResponseDto.builder()
                .id(userSalvato.getId())
                .email(userSalvato.getEmail())
                .role(userSalvato.getRoles())
                .createdAt(userSalvato.getCreatedAt())
                .updateAt(userSalvato.getUpdatedAt())
                .build();
    }
}
