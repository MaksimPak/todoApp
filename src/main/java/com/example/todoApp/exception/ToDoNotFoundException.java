package com.example.todoApp.exception;


public class ToDoNotFoundException extends RuntimeException {

    public ToDoNotFoundException(Long id) {
        super("Could not find todo " + id);
    }
}