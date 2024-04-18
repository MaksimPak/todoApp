package com.example.todoApp.repository;

import com.example.todoApp.entities.ToDoRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDoRecord, UUID> {
    List<ToDoRecord> findByCreatedBy(UUID id);
}
