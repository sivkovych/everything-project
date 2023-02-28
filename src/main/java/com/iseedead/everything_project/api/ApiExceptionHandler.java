package com.iseedead.everything_project.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@Component
@RestControllerAdvice
public class ApiExceptionHandler {
    public static ResponseEntity<ApiError> getError(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status)
                .body(new ApiError(status, exception.getMessage()));
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ApiError> handle(HttpException exception) {
        return getError(exception.getStatus(), exception);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handle(NoHandlerFoundException exception) {
        return getError(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handle(HttpRequestMethodNotSupportedException exception) {
        return getError(HttpStatus.METHOD_NOT_ALLOWED, exception);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ApiError> handle(HttpMediaTypeException exception) {
        return getError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handle(MethodArgumentTypeMismatchException exception) {
        return getError(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handle(EmptyResultDataAccessException exception) {
        return getError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(Exception exception) {
        return getError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}
