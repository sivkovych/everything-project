package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject.service.api.HttpException;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static java.lang.String.format;

public class CollectionNotFound extends HttpException {
    public CollectionNotFound(Long id) {
        super(HttpStatus.NOT_FOUND, format("Collection [id='%s'] not found", id));
    }

    public static Supplier<CollectionNotFound> get(Long id) {
        return () -> new CollectionNotFound(id);
    }
}
