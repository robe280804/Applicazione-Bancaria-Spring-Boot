package com.app_bancaria.my_bnl_application.security;

import com.app_bancaria.my_bnl_application.model.AuthProvider;
import com.app_bancaria.my_bnl_application.model.Role;
import com.app_bancaria.my_bnl_application.model.User;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import com.app_bancaria.my_bnl_application.security.jwt.JwtService;
import com.app_bancaria.my_bnl_application.security.model.UserPrincipal;
import com.app_bancaria.my_bnl_application.security.service.CustomerUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication auth) throws IOException {

        log.info("[OAUTH2] Handler oAuth2 in esecuzione ");

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) auth.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<User> userEsistente = userRepository.findByEmail(email);

        User user;

        if (userEsistente.isEmpty()){
            user = User.builder()
                    .email(email)
                    .roles(Set.of(Role.USER))
                    .authProvider(AuthProvider.GOOGLE)
                    .build();
            userRepository.save(user);
        } else {
            user = userEsistente.get();
        }

        UserPrincipal userExist = (UserPrincipal) customerUserDetailsService.loadUserByUsername(
                user.getEmail());

        String token = jwtService.generateToken(
                userExist.getId(), userExist.getEmail(), userExist.getAuthorities());

        log.info("[OAUTH2] Handler oAuth2 Token generato {}", token);

        // Imposta header e content-type per JSON
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Scrivi direttamente il token nella response
        String jsonResponse = "{\"token\":\"" + token + "\"}";
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
