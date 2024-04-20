package com.example.todoApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class TodoCreate {
    @NotBlank
    private String name;
    private MultipartFile image;
}
