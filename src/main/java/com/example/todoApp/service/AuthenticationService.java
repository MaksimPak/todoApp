package com.example.todoApp.service;

import com.example.todoApp.dto.AuthenticationResponse;
import com.example.todoApp.dto.RegisterRequest;
import com.example.todoApp.entities.Token;
import com.example.todoApp.entities.UserAccount;
import com.example.todoApp.entities.enums.TokenType;
import com.example.todoApp.repository.TokenRepository;
import com.example.todoApp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public  AuthenticationResponse register(RegisterRequest request) {
        System.out.println(request);
        UserAccount user = new UserAccount(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        UserAccount savedUser = userAccountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(
                jwtToken,
                refreshToken
        );

    }

    private void saveUserToken(UserAccount savedUser, String jwtToken) {
        var token = new Token(
                jwtToken,
                TokenType.BEARER,
                false,
                false,
                savedUser

        );
        tokenRepository.save(token);

    }
}
