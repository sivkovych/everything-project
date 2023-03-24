package com.sivkovych.everythingproject.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

@Converter
public final class MapConverter extends JSONConverter<Map<String, Object>> {
    public MapConverter(@Qualifier("globalMapper") ObjectMapper globalMapper) {
        super(globalMapper);
    }

    @Override
    protected Map<String, Object> getNullValue() {
        return new HashMap<>();
    }
}
