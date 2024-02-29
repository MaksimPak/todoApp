package com.example.todoApp.config;

import com.example.todoApp.exception.EmailAlreadyTaken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EmailAlreadyTaken.class,
            MethodArgumentNotValidException.class,
    })
    public final ResponseEntity<APIError> handleException(
            Exception ex,
            WebRequest request
    ) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof EmailAlreadyTaken emailAlreadyTaken) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleEmailAlreadyTaken(emailAlreadyTaken, headers, status, request);
        }
        else if (ex instanceof MethodArgumentNotValidException subEx) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMethodArgumentNotValid(subEx, subEx.getHeaders(), status, request);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    protected ResponseEntity<APIError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(validationError -> validationError.getField() + " " + validationError.getDefaultMessage())
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new APIError("validation error", errors), headers, status, request);
    }
    protected ResponseEntity<APIError> handleEmailAlreadyTaken(
            EmailAlreadyTaken ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, new APIError(ex.getMessage()), headers, status, request);
    }

    protected ResponseEntity<APIError> handleExceptionInternal(
            Exception ex, APIError body,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request
    ) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
