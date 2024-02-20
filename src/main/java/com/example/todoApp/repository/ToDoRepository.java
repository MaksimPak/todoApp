package com.example.todoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todoApp.entities.ToDoRecord;

public interface ToDoRepository extends JpaRepository<ToDoRecord, Long>  { }
