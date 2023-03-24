package com.sivkovych.everythingproject.service.api.v1.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject.service.ObjectMapperConfiguration;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@ForClass(ItemController.class)
@WebMvcTest(ItemController.class)
@Execution(ExecutionMode.SAME_THREAD)
abstract class ItemControllerTest {
    protected String getUrl(Long collectionId, Long itemId) {
        return String.format("%s/%s", getUrl(collectionId), itemId);
    }

    protected String getUrl(Long collectionId) {
        return String.format("/api/v1/collections/%s/items", collectionId);
    }

    protected Item getItem(Map<String, Object> data) {
        var item = Item.builder()
                .data(data)
                .build();
        ReflectionTestUtils.setField(item, "id", 1L);
        return item;
    }

    protected Collection getCollection(Item item) {
        var collection = new Collection(item.as());
        ReflectionTestUtils.setField(collection, "id", 1L);
        return collection;
    }

    protected ItemMapper getMapper() {
        return Mappers.getMapper(ItemMapper.class);
    }

    protected String asJson(Object value) throws JsonProcessingException {
        return ObjectMapperConfiguration.getGlobalMapper()
                .writeValueAsString(value);
    }
}
