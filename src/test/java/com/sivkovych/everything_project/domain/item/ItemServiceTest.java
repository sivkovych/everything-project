package com.sivkovych.everything_project.domain.item;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {
    @Test
    public void shouldReturnEntity_whenExists() {
        var item = new Item();
        item.setData(Map.of("key", "value"));
        var repository = mock(ItemRepository.class);
        when(repository.findById(1L)).thenReturn(Optional.of(item));
        var found = new ItemService(repository).findBy(1L);
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(item, found.get());
    }

    @Test
    public void shouldReturnEmpty_whenEntityDoesNotExist() {
        var repository = mock(ItemRepository.class);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        var found = new ItemService(repository).findBy(1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSaveEntity() {
        var item = new Item();
        item.setData(Map.of("key", "value"));
        var repository = mock(ItemRepository.class);
        when(repository.save(item)).then(invocation -> item);
        var saved = new ItemService(repository).save(item);
        assertNotNull(saved);
        assertEquals(item, saved);
    }

    @Test
    public void shouldDeleteEntity() {
        var repository = mock(ItemRepository.class);
        doNothing().when(repository)
                .deleteById(anyLong());
        new ItemService(repository).delete(1L);
        verify(repository).deleteById(1L);
    }
}
