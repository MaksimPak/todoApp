package com.example.todoApp.config;

import java.util.List;

public class APIError {
    private List<String> errors;

    public APIError(List<String> errors){
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
