package com.app_bancaria.my_bnl_application.security.service;

import com.app_bancaria.my_bnl_application.exception.EmailAlreadyExistsEx;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import com.app_bancaria.my_bnl_application.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), user.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato con email: " + email));
    }
}
