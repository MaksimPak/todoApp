package com.example.todoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    private final String accessToken;
    private final String refreshToken;

    public AuthenticationResponse(String jwtToken, String refreshToken) {
        this.accessToken = jwtToken;
        this.refreshToken = refreshToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}
