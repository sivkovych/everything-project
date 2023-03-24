package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject.service.api.HttpException;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static java.lang.String.format;

public class ItemNotFound extends HttpException {
    public ItemNotFound(Long collectionId, Long itemId) {
        super(HttpStatus.NOT_FOUND, format("Item [collectionId='%s', id='%s'] not found", collectionId, itemId));
    }

    public static Supplier<ItemNotFound> get(Long collectionId, Long itemId) {
        return () -> new ItemNotFound(collectionId, itemId);
    }
}
