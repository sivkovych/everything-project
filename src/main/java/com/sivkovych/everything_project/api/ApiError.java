package com.sivkovych.everything_project.api;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, int code, String message) {
    public ApiError(HttpException exception) {
        this(exception.getStatus(), exception.getStatus()
                .value(), exception.getMessage());
    }

    public ApiError(HttpStatus status, String message) {
        this(status, status.value(), message);
    }
}
