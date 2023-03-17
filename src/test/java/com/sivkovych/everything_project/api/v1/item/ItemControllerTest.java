package com.sivkovych.everything_project.api.v1.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sivkovych.everything_project.Application;
import com.sivkovych.everything_project.domain.item.Item;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@WebMvcTest(ItemController.class)
@Execution(ExecutionMode.SAME_THREAD)
abstract class ItemControllerTest {
    protected static final String ITEMS_URL = "/api/v1/items";

    protected Item getItem(Map<String, Object> data) {
        var item = Item.builder()
                .data(data)
                .build();
        ReflectionTestUtils.setField(item, "id", 1L);
        return item;
    }

    protected ItemMapper getMapper() {
        return Mappers.getMapper(ItemMapper.class);
    }

    protected String asJson(Object value) throws JsonProcessingException {
        return Application.getObjectMapper()
                .writeValueAsString(value);
    }
}
