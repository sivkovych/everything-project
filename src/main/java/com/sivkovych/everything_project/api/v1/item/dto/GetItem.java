package com.sivkovych.everything_project.api.v1.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetItem(Long id, Map<String, Object> data, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public GetItem(Long id, Map<String, Object> data) {
        this(id, data, null, null);
    }
}
