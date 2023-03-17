package com.sivkovych.everything_project.api.v1.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SetItem(Map<String, Object> data) {
    public SetItem() {
        this(new HashMap<>());
    }
}
