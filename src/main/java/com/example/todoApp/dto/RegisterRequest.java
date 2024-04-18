package com.example.todoApp.dto;

import com.example.todoApp.annotations.UniqueEmailConstraint;
import com.example.todoApp.entities.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    public RegisterRequest(String firstname, String lastName, String email, String password, Role role) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format(
                "RegisterRequest[firstName=%s, lastName=%s, email=%s]", firstName, lastName, email
        );
    }
}
