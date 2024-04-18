package com.example.todoApp.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class TodoCreate {
    @NotBlank
    private String name;
    private MultipartFile image;

    public TodoCreate(String name, MultipartFile image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public MultipartFile getImage() {
        return this.image;
    }

}
