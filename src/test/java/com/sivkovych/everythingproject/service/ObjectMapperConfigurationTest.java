package com.sivkovych.everythingproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ObjectMapperConfigurationTest {
    private final ObjectMapper globalMapper = ObjectMapperConfiguration.getGlobalMapper();

    @Test
    public void shouldUseSimpleDateTime_whenSerializing() {
        var now = LocalDateTime.now();
        var dto = new Dto(now);
        var json = assertDoesNotThrow(() -> globalMapper.writeValueAsString(dto));
        assertThat(json, is(equalTo(String.format("{\"dateTime\":\"%s\"}", now))));
    }

    @Test
    public void shouldUseSimpleDateTime_whenDeserializing() {
        var now = LocalDateTime.now();
        var json = String.format("{\"dateTime\":\"%s\"}", now);
        var dto = assertDoesNotThrow(() -> globalMapper.readValue(json, Dto.class));
        assertThat(dto, is(equalTo(new Dto(now))));
    }

    @Test
    public void shouldIgnoreUnknown_whenDeserializing() {
        var json = "{\"key\":\"value\"}";
        var dto = assertDoesNotThrow(() -> globalMapper.readValue(json, Dto.class));
        assertThat(dto, is(equalTo(new Dto(null))));
    }

    record Dto(LocalDateTime dateTime) {}
}
