package com.sivkovych.everythingproject.service.api.v1.item.dto;

import java.util.HashMap;
import java.util.Map;

public record SetItem(Map<String, Object> data) {
    public SetItem() {
        this(new HashMap<>());
    }
}
