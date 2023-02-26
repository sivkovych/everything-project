package com.iseedead.everything_project.api.v1.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iseedead.everything_project.api.v1.item.dto.CreateItem;
import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;

import static com.iseedead.everything_project.api.matchers.ResponseBodyMatchers.body;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SetItemTest extends ItemControllerTest {
    private final ItemMapper mapper;
    private final MockMvc mvc;
    private final ItemService service;
    private final ObjectMapper objectMapper;

    @Test
    public void shouldPersistEntity_whenRequestIsValid() throws Exception {
        var createItem = new CreateItem();
        var item = getMapper().to(createItem);
        when(mapper.to(createItem)).thenReturn(item);
        when(service.save(item)).thenAnswer(invocation -> {
            item.setId(1L);
            item.setCreatedAt(LocalDateTime.now());
            return item;
        });
        mvc.perform(post(ITEM_URL).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(createItem)))
                .andExpect(status().isOk())
                .andExpect(body().is(1L));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestIsEmpty() throws Exception {
        mvc.perform(post(ITEM_URL))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("No data passed"));
    }

    @Test
    public void shouldReturnUnsupportedMediaType_whenRequestHasWrongContentType() throws Exception {
        mvc.perform(post(ITEM_URL).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(body().isUnsupportedMediaType("Content-Type 'text/html;charset=UTF-8' is not supported"));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestHasWrongContent() throws Exception {
        var data = asJson(Map.of("kek", "double-kek"));
        mvc.perform(post(ITEM_URL).contentType(MediaType.APPLICATION_JSON)
                            .content(data))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("Invalid data passed"));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        var createItem = new CreateItem();
        var item = getMapper().to(createItem);
        when(mapper.to(createItem)).thenReturn(item);
        when(service.save(item)).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(post(ITEM_URL).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(createItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(body().isInternalServerError("Something went wrong"));
    }

    private String asJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
