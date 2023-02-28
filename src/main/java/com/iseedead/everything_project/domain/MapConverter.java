package com.iseedead.everything_project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapConverter extends JSONConverter<Map<String, Object>> {
    public MapConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
