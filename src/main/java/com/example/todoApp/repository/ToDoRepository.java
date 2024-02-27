package com.example.todoApp.repository;

import com.example.todoApp.entities.ToDoRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDoRecord, Long> { }
