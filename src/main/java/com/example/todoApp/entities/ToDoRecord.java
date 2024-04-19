package com.example.todoApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Entity
public class ToDoRecord extends BaseEntityAudit {
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String image;
}