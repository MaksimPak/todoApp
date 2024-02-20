package com.example.todoApp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.todoApp.exception.ToDoNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(404, "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(apiErrorResponse);
    }
    
    @ResponseBody
    @ExceptionHandler(ToDoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiErrorResponse> employeeNotFoundHandler(ToDoNotFoundException ex) {
      ApiErrorResponse apiErrorResponse = new ApiErrorResponse(404, ex.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(apiErrorResponse);
    }
}