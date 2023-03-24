package com.sivkovych.everythingproject.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class ObjectMapperConfiguration {
    public static ObjectMapper getGlobalMapper() {
        return new ObjectMapper().enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                .setDateFormat(new SimpleDateFormat())
                .registerModule(new JavaTimeModule());
    }

    @Bean("globalMapper")
    @Qualifier("globalMapper")
    public ObjectMapper globalMapper() {
        return getGlobalMapper();
    }
}
