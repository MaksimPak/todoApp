package com.example.todoApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class ToDoRecord extends BaseEntityAudit {

    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String image;

    protected ToDoRecord() {
    }

    public ToDoRecord(String name, String image) {
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format("Todo[id=%s, name='%s']", id, name);
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
