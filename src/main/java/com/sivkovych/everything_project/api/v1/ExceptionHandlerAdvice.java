package com.sivkovych.everything_project.api.v1;

import com.sivkovych.everything_project.api.ApiError;
import com.sivkovych.everything_project.api.ApiExceptionHandler;
import com.sivkovych.everything_project.api.v1.item.ItemNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestControllerV1.class)
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ItemNotFound.class)
    public ResponseEntity<ApiError> handle(ItemNotFound exception) {
        return ApiExceptionHandler.getError(exception.getStatus(), exception);
    }
}
