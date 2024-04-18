package com.example.todoApp.controller;

import com.example.todoApp.dto.TodoCreate;
import com.example.todoApp.dto.TodoDTO;
import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.entities.UserAccount;
import com.example.todoApp.exception.ToDoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;
import com.example.todoApp.service.S3Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1")
@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository repository;
    @Autowired
    private S3Service s3Service;

    @GetMapping("/todos")
    List<TodoDTO> all() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserAccount) authentication.getPrincipal();
        List<TodoDTO> todos = repository.findByCreatedBy(user.getId())
                .stream()
                .map(todo -> new TodoDTO(todo.getName(), s3Service.constructFullPath(todo.getImage())))
                .collect(Collectors.toList());

        return todos;

    }

    @PostMapping(value = "/todos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TodoDTO newTodo(@Valid TodoCreate todo) throws IOException {
        String imagePath = null;
        if (todo.getImage() != null) {
            imagePath = s3Service.uploadObject(todo.getImage());
        }
        ToDoRecord newTodo = new ToDoRecord(todo.getName(), imagePath);
        repository.save(newTodo);
        return new TodoDTO(newTodo.getName(), newTodo.getImage());
    }

    @GetMapping("/todos/{id}")
    TodoDTO getOne(@PathVariable UUID id) throws ToDoNotFoundException {
        ToDoRecord todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
        return new TodoDTO(todo.getName(), todo.getImage());
    }

    @PutMapping(value = "/todos/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TodoDTO changeTodo(
            @Valid TodoCreate editedTodo,
            @PathVariable UUID id
    ) throws ToDoNotFoundException, IOException {
        return repository.findById(id).map(todo -> {
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
            return new TodoDTO(todo.getName(), todo.getImage());
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
