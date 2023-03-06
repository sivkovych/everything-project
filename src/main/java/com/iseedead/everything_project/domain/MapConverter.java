package com.iseedead.everything_project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public final class MapConverter extends JSONConverter<Map<String, Object>> {
    public MapConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected Map<String, Object> getNullValue() {
        return new HashMap<>();
    }
}
