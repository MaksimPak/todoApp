package com.example.todoApp.controller;

import com.example.todoApp.dto.TodoCreate;
import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.entities.UserAccount;
import com.example.todoApp.exception.ToDoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/api/v1")
@RestController
public class ToDoController {
    @Autowired
    private S3Template s3Template;
	@Autowired
    private ToDoRepository repository;

    @GetMapping("/todos")
    List<ToDoRecord> all() {
        return repository.findAll();
    }


    @PostMapping(value = "/todos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ToDoRecord newTodo(@Valid TodoCreate todo) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();
        MultipartFile image = todo.getImage();
        String imagePath = null;
        if (image != null && image.getOriginalFilename() != null) {
            var s3Resource = s3Template.upload(
                    "todoapp-s3",
                    String.format("%s/", user.getId()) + image.getOriginalFilename(),
                    image.getInputStream(),
                    ObjectMetadata.builder().contentType(image.getContentType()).build()
            );
            imagePath = s3Resource.getLocation().getObject();
        }

        ToDoRecord newTodo = new ToDoRecord(todo.getName(), imagePath);
        return repository.save(newTodo);
    }

    @GetMapping("/todos/{id}")
    ToDoRecord getOne(@PathVariable Long id) throws ToDoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @PutMapping("/todos/{id}")
    ToDoRecord changeTodo(@Valid ToDoRecord newTodo, @PathVariable Long id) throws ToDoNotFoundException {
        return repository.findById(id).map(todo -> {
            todo.setName(newTodo.getName());
            return repository.save(todo);
        }).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @DeleteMapping("/todos/{id}")
    void deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
