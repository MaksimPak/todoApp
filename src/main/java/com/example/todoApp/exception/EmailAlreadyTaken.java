package com.example.todoApp.exception;

public class EmailAlreadyTaken extends RuntimeException{
    public EmailAlreadyTaken(String email) {
        super(email + "is already taken");
    }
}
