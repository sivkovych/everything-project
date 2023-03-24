package com.sivkovych.everythingproject.service.api.v1.item.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record GetItem(Long id, Long collectionId, Map<String, Object> data, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
    public GetItem(Long id, Long collectionId, Map<String, Object> data) {
        this(id, collectionId, data, LocalDateTime.now(), null);
    }

    public GetItem(Long id, Long collectionId) {
        this(id, collectionId, new HashMap<>(), LocalDateTime.now(), null);
    }
}
