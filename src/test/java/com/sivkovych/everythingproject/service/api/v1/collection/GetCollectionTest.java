package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.ResponseBodyMatchers;
import com.sivkovych.everythingproject.service.domain.collection.CollectionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ForMethod("get(Long id)")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GetCollectionTest extends CollectionControllerTest {
    private final CollectionMapper mapper;
    private final MockMvc mvc;
    private final CollectionService service;

    @Test
    public void shouldReturnCollection_whenValidId() throws Exception {
        var collection = getCollection();
        var getCollection = getMapper().from(collection);
        when(service.findBy(collection.getId())).thenReturn(Optional.of(collection));
        when(mapper.from(collection)).thenReturn(getCollection);
        mvc.perform(get(getUrl(collection.getId())))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.body()
                                   .is(getCollection));
    }

    @Test
    public void shouldReturnNotFound_whenInvalidId() throws Exception {
        when(service.findBy(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get(getUrl(1L)))
                .andExpect(status().isNotFound())
                .andExpect(ResponseBodyMatchers.body()
                                   .isError(new CollectionNotFound(1L)));
    }

    @Test
    public void shouldReturnBadRequest_whenIdIsNotNumber() throws Exception {
        mvc.perform(get(getUrl(null)))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("Failed to convert value of type 'java.lang.String' to required "
                                                         + "type 'java.lang.Long'; For input string: \"null\""));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        when(service.findBy(anyLong())).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(get(getUrl(anyLong())))
                .andExpect(status().isInternalServerError())
                .andExpect(ResponseBodyMatchers.body()
                                   .isInternalServerError("Something went wrong"));
    }
}
