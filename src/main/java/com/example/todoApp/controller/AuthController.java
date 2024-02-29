package com.example.todoApp.controller;

import com.example.todoApp.dto.AuthenticationRequest;
import com.example.todoApp.dto.AuthenticationResponse;
import com.example.todoApp.dto.RegisterRequest;
import com.example.todoApp.exception.EmailAlreadyTaken;
import com.example.todoApp.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(
            @Valid @RequestBody RegisterRequest request
            ) throws EmailAlreadyTaken {
        return authenticationService.register(request);
    }
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @Valid @RequestBody AuthenticationRequest request
            ) throws UsernameNotFoundException {
        return authenticationService.authenticate(request);
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, UsernameNotFoundException {
        authenticationService.refreshToken(request, response);
    }
}
