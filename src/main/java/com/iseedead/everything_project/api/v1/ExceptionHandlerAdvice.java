package com.iseedead.everything_project.api.v1;

import com.iseedead.everything_project.api.v1.item.ItemNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@ControllerAdvice(annotations = RestControllerV1.class)
public class ExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler(ItemNotFound.class)
    public ResponseEntity<ErrorDTO> handle(ItemNotFound exception) {
        return getError(exception.getStatus(), exception);
    }

    @ResponseBody
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorDTO> handle(HttpException exception) {
        return getError(exception.getStatus(), exception);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorDTO> handle(HttpMediaTypeException exception) {
        return getError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentTypeMismatchException exception) {
        return getError(HttpStatus.BAD_REQUEST, exception);
    }

    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorDTO> handle(EmptyResultDataAccessException exception) {
        return getError(HttpStatus.NOT_FOUND, exception);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handle(Exception exception) {
        return getError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorDTO> getError(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status)
                .body(new ErrorDTO(status, exception.getMessage()));
    }
}
