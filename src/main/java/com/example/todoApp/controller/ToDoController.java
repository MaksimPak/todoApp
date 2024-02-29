package com.example.todoApp.controller;

import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.exception.ToDoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1")
@RestController
public class ToDoController {
	@Autowired
    private ToDoRepository repository;

    @GetMapping("/todos")
    List<ToDoRecord> all() {
        return repository.findAll();
    }


    @PostMapping("/todos")
    ToDoRecord newTodo(@Valid @RequestBody ToDoRecord newTodo) {
        return repository.save(newTodo);
    }

    @GetMapping("/todos/{id}")
    ToDoRecord getOne(@PathVariable Long id) throws ToDoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @PutMapping("/todos/{id}")
    ToDoRecord changeTodo(@Valid @RequestBody ToDoRecord newTodo, @PathVariable Long id) throws ToDoNotFoundException {
        return repository.findById(id).map(todo -> {
            todo.setName(newTodo.getName());
            return repository.save(todo);
        }).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @DeleteMapping("/todos/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
