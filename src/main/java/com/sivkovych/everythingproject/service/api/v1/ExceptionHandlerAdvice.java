package com.sivkovych.everythingproject.service.api.v1;

import com.sivkovych.everythingproject.service.api.ApiError;
import com.sivkovych.everythingproject.service.api.ApiExceptionHandler;
import com.sivkovych.everythingproject.service.api.HttpException;
import com.sivkovych.everythingproject.service.api.v1.item.ItemNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestControllerV1.class)
public class ExceptionHandlerAdvice {
    public static <T> Supplier<HttpException> getException(Optional<T> optional) {
        return HttpException.get(HttpStatus.BAD_REQUEST, optional.isEmpty()
                ? "No data passed"
                : "Invalid data passed");
    }

    @ExceptionHandler(ItemNotFound.class)
    public ResponseEntity<ApiError> handle(ItemNotFound exception) {
        return ApiExceptionHandler.getError(exception.getStatus(), exception);
    }
}
