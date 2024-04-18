package com.example.todoApp.dto;

public class TodoDTO {
    private String name;
    private String image;

    public TodoDTO(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }
}
