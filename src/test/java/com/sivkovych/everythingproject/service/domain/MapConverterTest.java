package com.sivkovych.everythingproject.service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.sivkovych.everythingproject.service.ObjectMapperConfiguration.getGlobalMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ForClass(MapConverter.class)
class MapConverterTest {
    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ForMethod("convertToDatabaseColumn(Map<String, Object> attribute)")
    public void shouldConvertMapToDBColumn() throws JsonProcessingException {
        var attribute = Map.of("nested-1", Map.of("nested-2", Map.of("numeric", 1), "string", "lol"));
        var converter = new MapConverter(getGlobalMapper());
        var dbValue = converter.convertToDatabaseColumn((Map) attribute);
        assertNotNull(dbValue);
        assertEquals(getTestMapper().writeValueAsString(attribute), dbValue);
    }

    @Test
    @ForMethod("convertToDatabaseColumn(Map<String, Object> attribute)")
    public void shouldConvertMapToDBColumn_whenMapIsNull() {
        var converter = new MapConverter(getGlobalMapper());
        var dbValue = converter.convertToDatabaseColumn(null);
        assertNotNull(dbValue);
        assertEquals("{}", dbValue);
    }

    @Test
    @ForMethod("convertToDatabaseColumn(Map<String, Object> attribute)")
    public void shouldConvertMapToDBColumn_whenMapIsEmpty() {
        var converter = new MapConverter(getGlobalMapper());
        var dbValue = converter.convertToDatabaseColumn(Map.of());
        assertNotNull(dbValue);
        assertEquals("{}", dbValue);
    }

    @Test
    @ForMethod("convertToEntityAttribute(String dbData)")
    public void shouldConvertStringToMap() throws JsonProcessingException {
        var value = Map.of("nested-1", Map.of("nested-2", Map.of("numeric", 1), "string", "lol"));
        var dbValue = getTestMapper().writeValueAsString(value);
        var attribute = new MapConverter(getGlobalMapper()).convertToEntityAttribute(dbValue);
        assertNotNull(attribute);
        assertEquals(value, attribute);
    }

    @Test
    @ForMethod("convertToEntityAttribute(String dbData)")
    public void shouldConvertStringToMap_whenStringIsNull() {
        var attribute = new MapConverter(getGlobalMapper()).convertToEntityAttribute(null);
        assertNotNull(attribute);
        assertEquals(Map.of(), attribute);
    }

    @Test
    @ForMethod("convertToEntityAttribute(String dbData)")
    public void shouldConvertStringToMap_whenStringIsEmpty() {
        var attribute = new MapConverter(getGlobalMapper()).convertToEntityAttribute("");
        assertNotNull(attribute);
        assertEquals(Map.of(), attribute);
    }

    private ObjectMapper getTestMapper() {
        return new ObjectMapper();
    }
}
