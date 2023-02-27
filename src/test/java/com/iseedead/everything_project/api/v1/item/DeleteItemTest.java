package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import static com.iseedead.everything_project.api.matchers.ResponseBodyMatchers.body;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DeleteItemTest extends ItemControllerTest {
    private final MockMvc mvc;
    private final ItemService service;

    @Test
    public void shouldRemoveItem_whenValidId() throws Exception {
        mvc.perform(delete(ITEMS_URL + "/" + 1L))
                .andExpect(status().isOk())
                .andExpect(body().isEmpty());
        verify(service).delete(1L);
    }

    @Test
    public void shouldReturnNotFound_whenInvalidId() throws Exception {
        doThrow(new EmptyResultDataAccessException("No [item] entity with id 1 exists", 1)).when(service)
                .delete(anyLong());
        mvc.perform(delete(ITEMS_URL + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(body().isNotFound("No [item] entity with id 1 exists"));
    }

    @Test
    public void shouldReturnBadRequest_whenIdIsNotNumber() throws Exception {
        mvc.perform(delete(ITEMS_URL + "/null"))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("Failed to convert value of type 'java.lang.String' to required "
                                                       + "type 'java.lang.Long'; For input string: \"null\""));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        doThrow(new RuntimeException("Something went wrong")).when(service)
                .delete(anyLong());
        mvc.perform(delete(ITEMS_URL + "/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(body().isInternalServerError("Something went wrong"));
    }
}
