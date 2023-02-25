package com.iseedead.everything_project.api.v1.item.dto;

import java.util.Map;

public record GetItem(Long id, Map<String, Object> data) {}
