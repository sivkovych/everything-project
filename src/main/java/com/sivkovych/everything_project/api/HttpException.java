package com.sivkovych.everything_project.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class HttpException extends Exception {
    private final HttpStatus status;
    private final String message;

    public HttpException(HttpStatus status, String message, Object... arguments) {
        this.status = status;
        this.message = String.format(message, arguments);
    }

    public static Supplier<HttpException> get(HttpStatus status, String message) {
        return () -> new HttpException(status, message);
    }

    public static Supplier<HttpException> get(HttpStatus status, String message, Object... arguments) {
        return () -> new HttpException(status, message, arguments);
    }
}
