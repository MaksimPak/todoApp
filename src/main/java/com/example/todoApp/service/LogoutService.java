package com.example.todoApp.service;

import com.example.todoApp.entities.Token;
import com.example.todoApp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LogoutService implements LogoutHandler {
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }
        final String jwt = authHeader.substring(7);
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if (token != null) {
            token.expired = true;
            token.revoked = true;
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
    }
}
