package com.example.todoApp.dto;

import com.example.todoApp.annotations.UniqueEmailConstraint;
import com.example.todoApp.entities.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class RegisterRequest {
    private String firstName;
    private String lastName;
    @NotBlank
    @Email
    @UniqueEmailConstraint
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Role role;
}
