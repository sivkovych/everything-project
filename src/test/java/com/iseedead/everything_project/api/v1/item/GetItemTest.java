package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static com.iseedead.everything_project.api.matchers.ResponseBodyMatchers.body;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(ItemService.class), @MockBean(ItemMapper.class)})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GetItemTest extends ItemControllerTest {
    private final ItemMapper mapper;
    private final MockMvc mvc;
    private final ItemService service;

    @Test
    public void shouldReturnItem_whenValidId() throws Exception {
        var item = getItem(Map.of("kek", Map.of("pew", "meow")));
        var getItem = getMapper().from(item);
        when(service.findBy(item.getId())).thenReturn(Optional.of(item));
        when(mapper.from(item)).thenReturn(getItem);
        mvc.perform(get(ITEM_URL + "/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(body().is(getItem));
    }

    @Test
    public void shouldReturnNotFound_whenInvalidId() throws Exception {
        when(service.findBy(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get(ITEM_URL + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(body().isError(new ItemNotFound(1L)));
    }

    @Test
    public void shouldReturnBadRequest_whenIdIsNotNumber() throws Exception {
        mvc.perform(get(ITEM_URL + "/null"))
                .andExpect(status().isBadRequest())
                .andExpect(body().isBadRequest("Failed to convert value of type 'java.lang.String' to required "
                                                       + "type 'java.lang.Long'; For input string: \"null\""));
    }

    @Test
    public void shouldReturnInternalError() throws Exception {
        when(service.findBy(anyLong())).thenThrow(new RuntimeException("Something went wrong"));
        mvc.perform(get(ITEM_URL + "/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(body().isInternalServerError("Something went wrong"));
    }
}
