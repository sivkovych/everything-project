package com.iseedead.everything_project.api.v1;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@RequiredArgsConstructor
public class Error {
    private final String message;
    private final HttpStatus status;
}
