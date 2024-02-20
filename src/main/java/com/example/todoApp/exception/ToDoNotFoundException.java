package com.example.todoApp.exception;


@SuppressWarnings("serial")
public class ToDoNotFoundException extends RuntimeException {

	public ToDoNotFoundException(Long id) {
	    super("Could not find todo " + id);
	  }
}