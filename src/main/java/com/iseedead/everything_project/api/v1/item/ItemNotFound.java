package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.api.v1.HttpException;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static java.lang.String.format;

public class ItemNotFound extends HttpException {
    public ItemNotFound(Long id) {
        super(HttpStatus.NOT_FOUND, format("Item [id=%s] not found", id));
    }

    public static Supplier<ItemNotFound> get(Long id) {
        return () -> new ItemNotFound(id);
    }
}
