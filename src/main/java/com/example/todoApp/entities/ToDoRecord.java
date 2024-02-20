package com.example.todoApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;



@Entity
public class ToDoRecord extends BaseEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  @NotBlank(message = "Name is required")
  private String name;

  protected ToDoRecord() {}

  public ToDoRecord(String name) {
    this.name = name;
    
  }

  @Override
  public String toString() {
    return String.format(
        "Todo[id=%d, name='%s']",
        id, name);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
