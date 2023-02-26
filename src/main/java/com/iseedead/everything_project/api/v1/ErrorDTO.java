package com.iseedead.everything_project.api.v1;

import org.springframework.http.HttpStatus;

public record ErrorDTO(HttpStatus status, int code, String message) {
    public ErrorDTO(HttpException exception) {
        this(exception.getStatus(), exception.getStatus()
                .ordinal(), exception.getMessage());
    }

    public ErrorDTO(HttpStatus status, String message) {
        this(status, status.ordinal(), message);
    }
}
