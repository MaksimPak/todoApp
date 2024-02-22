package com.example.todoApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoApp.entities.ToDoRecord;
import com.example.todoApp.exception.ToDoNotFoundException;
import com.example.todoApp.repository.ToDoRepository;

import jakarta.validation.Valid;

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
	  ToDoRecord newEmployee(@Valid @RequestBody ToDoRecord newTodo) {
	    return repository.save(newTodo);
	  }
	  
	  @GetMapping("/todos/{id}")
	  ToDoRecord getOne(@PathVariable Long id) {
		  return repository.findById(id).orElseThrow(()-> new ToDoNotFoundException(id));
	  }
	  
	  @PutMapping("/todos/{id}")
	  ToDoRecord changeTodo(@Valid @RequestBody ToDoRecord newTodo, @PathVariable Long id) {
		  return repository.findById(id).map(todo -> {
			  todo.setName(newTodo.getName());
			  return repository.save(todo);
		  }).orElseThrow(()-> new ToDoNotFoundException(id));
	  }
	  
	  @DeleteMapping("/todos/{id}")
	  void deleteEmployee(@PathVariable Long id) {
	    repository.deleteById(id);
	  }

}
