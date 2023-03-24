package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.ResponseBodyMatchers;
import com.sivkovych.everythingproject.service.api.v1.item.dto.SetItem;
import com.sivkovych.everythingproject.service.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ForMethod("create(Long collectionId, Optional<SetItem> item)")
@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CreateItemTest extends ItemControllerTest {
    private final ItemMapper mapper;
    private final MockMvc mvc;
    private final ItemService service;

    @Test
    public void shouldPersistEntity_whenRequestIsValid() throws Exception {
        var setItem = new SetItem();
        var item = getMapper().to(1L, setItem);
        var getItem = getMapper().from(item);
        when(mapper.to(1L, setItem)).thenReturn(item);
        when(mapper.from(item)).thenReturn(getItem);
        when(service.save(item)).thenAnswer(invocation -> item);
        mvc.perform(post(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setItem)))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.body()
                                   .is(getItem));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestIsEmpty() throws Exception {
        mvc.perform(post(getUrl(1L)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("No data passed"));
    }

    @Test
    public void shouldReturnUnsupportedMediaType_whenRequestHasWrongContentType() throws Exception {
        mvc.perform(post(getUrl(1L)).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(ResponseBodyMatchers.body()
                                   .isUnsupportedMediaType("Content-Type 'text/html' is not supported"));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestHasWrongContent() throws Exception {
        var data = asJson(Map.of("kek", "double-kek"));
        mvc.perform(post(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(data))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("Invalid data passed"));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        var setItem = new SetItem();
        var item = getMapper().to(1L, setItem);
        when(mapper.to(1L, setItem)).thenReturn(item);
        when(service.save(item)).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(post(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(ResponseBodyMatchers.body()
                                   .isInternalServerError("Something went wrong"));
    }
}
