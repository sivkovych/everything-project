package com.iseedead.everything_project.api.v1;

import com.iseedead.everything_project.api.v1.item.ItemNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(annotations = {RestControllerV1.class})
public class ExceptionHandlerAdviceV1 {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFound.class)
    public Error handleItemAbsence(ItemNotFound exception) {
        return Error.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
