package com.example.todoApp.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.repository.ToDoRepository;

import jakarta.validation.Valid;

@RequestMapping(value = "/api/v1")
@RestController
public class ToDoService {
	  private final ToDoRepository repository;
	  
	  ToDoService(ToDoRepository repository) {
		  this.repository = repository;
	  }
	  
	  @GetMapping("/todos")
	  List<ToDoRecord> all() {
	    return repository.findAll();
	  }
	  

	  @PostMapping("/todos")
	  ToDoRecord newEmployee(@Valid @RequestBody ToDoRecord newTodo) {
	    return repository.save(newTodo);
	  }

}
