package com.iseedead.everything_project.api.v1.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateItem(@JsonProperty Map<String, Object> data) {}
