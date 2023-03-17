package com.sivkovych.everything_project.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.sivkovych.everything_project.Application.getObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapConverterTest {
    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void shouldConvertMapToDBColumn() throws JsonProcessingException {
        var attribute = Map.of("nested-1", Map.of("nested-2", Map.of("numeric", 1), "string", "lol"));
        var converter = new MapConverter(getObjectMapper());
        var dbValue = converter.convertToDatabaseColumn((Map) attribute);
        assertNotNull(dbValue);
        assertEquals(getTestMapper().writeValueAsString(attribute), dbValue);
    }

    @Test
    public void shouldConvertMapToDBColumn_whenMapIsNull() {
        var converter = new MapConverter(getObjectMapper());
        var dbValue = converter.convertToDatabaseColumn(null);
        assertNotNull(dbValue);
        assertEquals("{ }", dbValue);
    }

    @Test
    public void shouldConvertMapToDBColumn_whenMapIsEmpty() {
        var converter = new MapConverter(getObjectMapper());
        var dbValue = converter.convertToDatabaseColumn(Map.of());
        assertNotNull(dbValue);
        assertEquals("{ }", dbValue);
    }

    @Test
    public void shouldConvertStringToMap() throws JsonProcessingException {
        var value = Map.of("nested-1", Map.of("nested-2", Map.of("numeric", 1), "string", "lol"));
        var dbValue = getTestMapper().writeValueAsString(value);
        var attribute = new MapConverter(getObjectMapper()).convertToEntityAttribute(dbValue);
        assertNotNull(attribute);
        assertEquals(value, attribute);
    }

    @Test
    public void shouldConvertStringToMap_whenStringIsNull() {
        var attribute = new MapConverter(getObjectMapper()).convertToEntityAttribute(null);
        assertNotNull(attribute);
        assertEquals(Map.of(), attribute);
    }

    @Test
    public void shouldConvertStringToMap_whenStringIsEmpty() {
        var attribute = new MapConverter(getObjectMapper()).convertToEntityAttribute("");
        assertNotNull(attribute);
        assertEquals(Map.of(), attribute);
    }

    private ObjectMapper getTestMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }
}
