package com.example.todoApp.config;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class APIError {
    private String detail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public APIError(String detail){
        this.detail = detail;
    }
    public APIError(String detail, List<String> errors){
        this.detail = detail;
        this.errors = errors;
    }
    public String getDetail() {
        return detail;
    }
    public List<String> getErrors() { return errors; }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
