package com.example.todoApp.exception;

import com.example.todoApp.entities.ToDoRecord;

import java.util.UUID;

public class TodoNotFoundException extends EntityNotFoundException {
    public TodoNotFoundException(UUID id) {
        super(ToDoRecord.class, "id" , id.toString());
    }
}
