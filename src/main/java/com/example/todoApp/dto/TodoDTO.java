package com.example.todoApp.dto;

import java.util.UUID;

public class TodoDTO {

    private UUID id;
    private String name;
    private String image;

    public TodoDTO(UUID id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public UUID getId() { return this.id; }
    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }
}
