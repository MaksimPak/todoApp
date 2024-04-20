package com.example.todoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private final String accessToken;
    private final String refreshToken;
}
