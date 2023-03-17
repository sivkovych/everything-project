package com.sivkovych.everything_project.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
@RequiredArgsConstructor
public abstract class JSONConverter<T> implements AttributeConverter<T, String> {
    private static final String EMPTY_JSON = "{ }";
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            if (attribute == null) {
                return EMPTY_JSON;
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Failed to write JSON attribute to a database", jsonProcessingException);
            return EMPTY_JSON;
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return dbData == null || dbData.isEmpty() || dbData.isBlank()
                    ? getNullValue()
                    : objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Failed to read JSON attribute from a database", jsonProcessingException);
            return getNullValue();
        }
    }

    protected abstract T getNullValue();
}
