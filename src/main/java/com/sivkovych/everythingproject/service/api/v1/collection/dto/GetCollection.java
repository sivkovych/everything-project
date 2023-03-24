package com.sivkovych.everythingproject.service.api.v1.collection.dto;

import com.sivkovych.everythingproject.service.api.v1.item.dto.GetItem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public record GetCollection(Long id, String name, int size, Set<GetItem> items, LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
    public GetCollection(Long id, String name) {
        this(id, name, new HashSet<>());
    }

    public GetCollection(Long id, String name, Set<GetItem> items) {
        this(id, name, items, LocalDateTime.now(), null);
    }

    public GetCollection(Long id, String name, Set<GetItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, name, items == null
                ? 0
                : items.size(), items == null
                     ? new HashSet<>()
                     : items, createdAt, updatedAt);
    }
}
