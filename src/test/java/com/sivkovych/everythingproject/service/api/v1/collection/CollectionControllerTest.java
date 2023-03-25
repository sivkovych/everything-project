package com.sivkovych.everythingproject.service.api.v1.collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject.service.ObjectMapperConfiguration;
import com.sivkovych.everythingproject.service.api.v1.item.ItemMapper;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import com.sivkovych.everythingproject.service.domain.collection.CollectionService;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@ForClass(CollectionController.class)
@WebMvcTest(CollectionController.class)
@Execution(ExecutionMode.SAME_THREAD)
@MockBeans({@MockBean(CollectionService.class), @MockBean(CollectionMapper.class)})
abstract class CollectionControllerTest {
    protected String getUrl(Long id) {
        return String.format("%s/%s", getUrl(), id);
    }

    protected String getUrl() {
        return "/api/v1/collections";
    }

    protected Collection getCollection() {
        var collection = new Collection(new Item(Map.of()).as());
        ReflectionTestUtils.setField(collection, "id", 1L);
        return collection;
    }

    protected CollectionMapper getMapper() {
        var collectionMapper = Mappers.getMapper(CollectionMapper.class);
        ReflectionTestUtils.setField(collectionMapper, "itemMapper", Mappers.getMapper(ItemMapper.class));
        return collectionMapper;
    }

    protected String asJson(Object value) throws JsonProcessingException {
        return ObjectMapperConfiguration.getGlobalMapper()
                .writeValueAsString(value);
    }
}
