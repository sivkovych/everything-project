package com.iseedead.everything_project.api.v1.item.dto;

import java.util.HashMap;
import java.util.Map;

public record CreateItem(Map<String, Object> data) {
    public CreateItem() {
        this(new HashMap<>());
    }
}
