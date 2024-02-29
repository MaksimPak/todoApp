package com.example.todoApp.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationError extends RuntimeException{
    List<ObjectError> errors;
    public ValidationError(List<ObjectError> errors) {
        this.errors = errors;
    }
    public static ValidationError createWith(List<ObjectError> errors) {
        return new ValidationError(errors);
    }
    public List<ObjectError> getErrors() {
        return errors;
    }
}
