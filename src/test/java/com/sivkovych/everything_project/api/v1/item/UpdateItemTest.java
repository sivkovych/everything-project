package com.sivkovych.everything_project.api.v1.item;

import com.sivkovych.everything_project.api.v1.item.dto.SetItem;
import com.sivkovych.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static com.sivkovych.everything_project.api.ResponseBodyMatchers.body;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UpdateItemTest extends ItemControllerTest {
    private final ItemMapper mapper;
    private final MockMvc mvc;
    private final ItemService service;

    @Test
    public void shouldPersistEntity_whenRequestIsValid() throws Exception {
        var setItem = new SetItem(Map.of("key", "value"));
        var item = getMapper().to(1L, setItem);
        var getItem = getMapper().from(item);
        when(mapper.to(1L, setItem)).thenReturn(item);
        when(mapper.from(item)).thenReturn(getItem);
        when(service.save(item)).thenAnswer(invocation -> item);
        mvc.perform(put(ITEMS_URL + "/1").contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setItem)))
                .andExpect(status().isOk())
                .andExpect(body().is(getItem));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestIsEmpty() throws Exception {
        mvc.perform(put(ITEMS_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("No data passed"));
    }

    @Test
    public void shouldReturnUnsupportedMediaType_whenRequestHasWrongContentType() throws Exception {
        mvc.perform(put(ITEMS_URL + "/1").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(body().isUnsupportedMediaType("Content-Type 'text/html' is not supported"));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestHasWrongContent() throws Exception {
        var data = asJson(Map.of("kek", "double-kek"));
        mvc.perform(put(ITEMS_URL + "/1").contentType(MediaType.APPLICATION_JSON)
                            .content(data))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("Invalid data passed"));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        var setItem = new SetItem(Map.of("key", "value"));
        var item = getMapper().to(1L, setItem);
        when(mapper.to(1L, setItem)).thenReturn(item);
        when(service.save(item)).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(put(ITEMS_URL + "/1").contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(body().isInternalServerError("Something went wrong"));
    }
}
