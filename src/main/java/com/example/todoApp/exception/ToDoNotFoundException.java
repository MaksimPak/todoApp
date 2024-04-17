package com.example.todoApp.exception;


import java.util.UUID;

public class ToDoNotFoundException extends RuntimeException {

    public ToDoNotFoundException(UUID id) {
        super("Could not find todo " + id);
    }
}