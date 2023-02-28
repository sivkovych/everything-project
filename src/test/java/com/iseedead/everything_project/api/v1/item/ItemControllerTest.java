package com.iseedead.everything_project.api.v1.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iseedead.everything_project.domain.item.Item;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import static com.iseedead.everything_project.Application.getObjectMapper;

@WebMvcTest(ItemController.class)
abstract class ItemControllerTest {
    protected static final String ITEMS_URL = "/api/v1/items";

    protected Item getItem(Map<String, Object> data) {
        var item = new Item();
        item.setId(new Random().nextLong());
        item.setData(data);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }

    protected ItemMapper getMapper() {
        return Mappers.getMapper(ItemMapper.class);
    }

    protected String asJson(Object value) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(value);
    }
}
