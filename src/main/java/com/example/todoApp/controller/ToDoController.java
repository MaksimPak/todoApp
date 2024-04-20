package com.example.todoApp.controller;

import com.example.todoApp.dto.TodoCreate;
import com.example.todoApp.dto.TodoDTO;
import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.entities.UserAccount;
import com.example.todoApp.exception.EntityNotFoundException;
import com.example.todoApp.exception.TodoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;
import com.example.todoApp.service.S3Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1")
@AllArgsConstructor
@RestController
public class ToDoController {

    private ToDoRepository repository;
    private S3Service s3Service;

    @GetMapping("/todos")
    List<TodoDTO> all() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserAccount) authentication.getPrincipal();

        return repository.findByCreatedBy(user.getId())
                .stream()
                .map(todo -> TodoDTO.builder()
                        .id(todo.getId())
                        .name(todo.getName())
                        .image(s3Service.constructFullPath(todo.getImage()))
                        .build())
                .collect(Collectors.toList());

    }

    @PostMapping(value = "/todos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TodoDTO newTodo(@Valid TodoCreate todo) throws IOException {
        String imagePath = null;
        if (todo.getImage() != null) imagePath = s3Service.uploadObject(todo.getImage());

        ToDoRecord newTodo = ToDoRecord.builder()
                .name(todo.getName())
                .image(imagePath)
                .build();

        repository.save(newTodo);
        return TodoDTO.builder()
                .id(newTodo.getId())
                .name(newTodo.getName())
                .image(s3Service.constructFullPath(newTodo.getImage()))
                .build();
    }

    @GetMapping("/todos/{id}")
    TodoDTO getOne(@PathVariable UUID id) throws TodoNotFoundException {
        ToDoRecord todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));

        return TodoDTO.builder()
                .id(todo.getId())
                .name(todo.getName())
                .image(todo.getImage())
                .build();
    }

    @PutMapping(value = "/todos/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TodoDTO changeTodo(
            @Valid TodoCreate editedTodo,
            @PathVariable UUID id
    ) throws EntityNotFoundException, IOException {
        return repository.findById(id).map(todo -> {
            if (todo.getImage() != null) s3Service.deleteObject(todo.getImage());
            if (editedTodo.getImage() != null) {
                try {
                    String imagePath = s3Service.uploadObject(editedTodo.getImage());
                    todo.setImage(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            todo.setName(editedTodo.getName());
            repository.save(todo);

            return TodoDTO.builder()
                    .id(todo.getId())
                    .name(todo.getName())
                    .image(s3Service.constructFullPath(todo.getImage()))
                    .build();
        }).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @DeleteMapping("/todos/{id}")
    void deleteTodo(@PathVariable UUID id) {
        Optional<ToDoRecord> todo = repository.findById(id);
        if (todo.isPresent()){
            repository.deleteById(id);
            if(todo.get().getImage() != null) s3Service.deleteObject(todo.get().getImage());
        }

    }

    @DeleteMapping("/todos/{id}/deleteImage")
    void deleteTodoImage(@PathVariable UUID id) {
        ToDoRecord todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        if (todo.getImage() != null) s3Service.deleteObject(todo.getImage());
        todo.setImage(null);
        repository.save(todo);
    }

}
