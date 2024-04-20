package com.example.todoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TodoDTO {
    private UUID id;
    private String name;
    private String image;
}
