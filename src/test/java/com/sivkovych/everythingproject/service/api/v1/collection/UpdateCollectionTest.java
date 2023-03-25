package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.ResponseBodyMatchers;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.SetCollection;
import com.sivkovych.everythingproject.service.domain.collection.CollectionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ForMethod("update(Long id, Optional<SetCollection> collection)")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UpdateCollectionTest extends CollectionControllerTest {
    private final CollectionMapper mapper;
    private final MockMvc mvc;
    private final CollectionService service;

    @Test
    public void shouldPersistEntity_whenRequestIsValid() throws Exception {
        var setCollection = new SetCollection("some-name");
        var collection = getMapper().to(1L, setCollection);
        var getCollection = getMapper().from(collection);
        when(mapper.to(1L, setCollection)).thenReturn(collection);
        when(mapper.from(collection)).thenReturn(getCollection);
        when(service.save(collection)).thenAnswer(invocation -> collection);
        mvc.perform(put(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setCollection)))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.body()
                                   .is(getCollection));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestIsEmpty() throws Exception {
        mvc.perform(put(getUrl(1L)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("No data passed"));
    }

    @Test
    public void shouldReturnUnsupportedMediaType_whenRequestHasWrongContentType() throws Exception {
        mvc.perform(put(getUrl(1L)).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(ResponseBodyMatchers.body()
                                   .isUnsupportedMediaType("Content-Type 'text/html' is not supported"));
    }

    @Test
    public void shouldReturnBadRequest_whenRequestHasWrongContent() throws Exception {
        var data = asJson(Map.of("kek", "double-kek"));
        mvc.perform(put(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(data))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("Invalid data passed"));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        var setCollection = new SetCollection("some-name");
        var collection = getMapper().to(1L, setCollection);
        when(mapper.to(1L, setCollection)).thenReturn(collection);
        when(service.save(collection)).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(put(getUrl(1L)).contentType(MediaType.APPLICATION_JSON)
                            .content(asJson(setCollection)))
                .andExpect(status().isInternalServerError())
                .andExpect(ResponseBodyMatchers.body()
                                   .isInternalServerError("Something went wrong"));
    }
}
