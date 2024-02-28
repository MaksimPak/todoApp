package com.example.todoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty
    private String accessToken;
    @JsonProperty
    private String refreshToken;

    public AuthenticationResponse(String jwtToken, String refreshToken) {
        this.accessToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
