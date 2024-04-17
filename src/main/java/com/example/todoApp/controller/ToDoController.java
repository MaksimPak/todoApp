package com.example.todoApp.controller;

import com.example.todoApp.dto.TodoCreate;
import com.example.todoApp.dto.TodoEdit;
import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.exception.ToDoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;
import com.example.todoApp.service.S3Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/api/v1")
@RestController
public class ToDoController {

	@Autowired
    private ToDoRepository repository;
    @Autowired
    private S3Service s3Service;

    @GetMapping("/todos")
    List<ToDoRecord> all() {
        return repository.findAll();
    }

    @PostMapping(value = "/todos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ToDoRecord newTodo(@Valid TodoCreate todo) throws IOException {
        String imagePath = null;
        if (todo.getImage() != null) {
            imagePath = s3Service.uploadObject(todo.getImage());
        }
        ToDoRecord newTodo = new ToDoRecord(todo.getName(), imagePath);
        return repository.save(newTodo);
    }

    @GetMapping("/todos/{id}")
    ToDoRecord getOne(@PathVariable UUID id) throws ToDoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @PutMapping(value="/todos/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ToDoRecord changeTodo(
            @Valid TodoEdit editedTodo,
            @PathVariable UUID id
    ) throws ToDoNotFoundException, IOException {
        return repository.findById(id).map(todo -> {
            if(editedTodo.getImage() != null) {
                try {
                    String imagePath = s3Service.uploadObject(editedTodo.getImage());
                    todo.setImage(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            todo.setName(editedTodo.getName());
            return repository.save(todo);
        }).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @DeleteMapping("/todos/{id}")
    void deleteTodo(@PathVariable UUID id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/todos/{id}/deleteImage")
    void deleteTodoImage(@PathVariable UUID id) {
        ToDoRecord todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
        todo.setImage(null);
        repository.save(todo);

    }

}
