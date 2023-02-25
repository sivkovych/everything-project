package com.iseedead.everything_project.api.v1.item;

import java.util.function.Supplier;

import static java.lang.String.format;

public class ItemNotFound extends Throwable {
    public ItemNotFound(Long id) {
        super(format("Item [id=%s] not found", id));
    }

    public static Supplier<ItemNotFound> get(Long id) {
        return () -> new ItemNotFound(id);
    }
}
