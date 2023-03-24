package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.ResponseBodyMatchers;
import com.sivkovych.everythingproject.service.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ForMethod("delete(Long collectionId, Long itemId)")
@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DeleteItemTest extends ItemControllerTest {
    private final MockMvc mvc;
    private final ItemService service;

    @Test
    public void shouldRemoveItem_whenValidId() throws Exception {
        mvc.perform(delete(getUrl(1L, 1L)))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.body()
                                   .isEmpty());
        verify(service).delete(1L, 1L);
    }

    @Test
    public void shouldReturnNotFound_whenInvalidId() throws Exception {
        doThrow(new EmptyResultDataAccessException("No item with [collectionId='1', id='1'] exists", 1)).when(service)
                .delete(anyLong(), anyLong());
        mvc.perform(delete(getUrl(1L, 1L)))
                .andExpect(status().isNotFound())
                .andExpect(ResponseBodyMatchers.body()
                                   .isNotFound("No item with [collectionId='1', id='1'] exists"));
    }

    @Test
    public void shouldReturnBadRequest_whenIdIsNotNumber() throws Exception {
        mvc.perform(delete(getUrl(null, null)))
                .andExpect(status().isBadRequest())
                .andExpect(ResponseBodyMatchers.body()
                                   .isBadRequest("Failed to convert value of type 'java.lang.String' to required "
                                                         + "type 'java.lang.Long'; For input string: \"null\""));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        doThrow(new RuntimeException("Something went wrong")).when(service)
                .delete(anyLong(), anyLong());
        mvc.perform(delete(getUrl(1L, 1L)))
                .andExpect(status().isInternalServerError())
                .andExpect(ResponseBodyMatchers.body()
                                   .isInternalServerError("Something went wrong"));
    }
}
