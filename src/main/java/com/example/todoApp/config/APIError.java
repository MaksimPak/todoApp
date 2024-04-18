package com.example.todoApp.config;

import java.util.List;

public record APIError(List<String> errors) {
}
