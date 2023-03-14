package com.iseedead.everything_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> log.info("Started [{}] at [{}]", applicationName, LocalDateTime.now());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return getObjectMapper();
    }
}
