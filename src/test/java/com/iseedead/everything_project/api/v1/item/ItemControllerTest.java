package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.domain.item.Item;
import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static com.iseedead.everything_project.api.matchers.ResponseBodyMatchers.body;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemControllerTest {
    private final ItemMapper mapper;
    private final MockMvc mvc;
    private final ItemController controller;
    private final ItemService service;

    @Test
    public void shouldBeLoaded() {
        assertThat(controller, is(notNullValue()));
    }

    @Test
    public void shouldReturnItem_whenValidId() throws Exception {
        var item = getItem(Map.of("kek", Map.of("pew", "meow")));
        var getItem = getMapper().itemToGetItem(item);
        when(service.findBy(item.getId())).thenReturn(Optional.of(item));
        when(mapper.itemToGetItem(item)).thenReturn(getItem);
        mvc.perform(get("/api/v1/item/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(body().isEqualTo(getItem));
    }

    private Item getItem(Map<String, Object> data) {
        var item = new Item();
        item.setId(new Random().nextLong());
        item.setData(data);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }

    private ItemMapper getMapper() {
        return Mappers.getMapper(ItemMapper.class);
    }
}
