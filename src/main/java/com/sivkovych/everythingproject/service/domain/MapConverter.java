package com.sivkovych.everythingproject.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public final class MapConverter extends JSONConverter<Map<String, Object>> {
    public MapConverter(ObjectMapper globalMapper) {
        super(globalMapper);
    }

    @Override
    protected Map<String, Object> getNullValue() {
        return new HashMap<>();
    }
}
