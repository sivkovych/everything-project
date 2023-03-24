package com.sivkovych.everythingproject.service.domain.item;

import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
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

@ForClass(ItemService.class)
class ItemServiceTest {
    @Test
    @ForMethod("findBy(Long collectionId, Long id)")
    public void shouldFindItemByCollectionIdAndId() {
        var item = new Item(Map.of("key", "value"));
        var repository = mock(ItemRepository.class);
        when(repository.findByCollectionIdAndId(1L, 1L)).thenReturn(Optional.of(item));
        var found = new ItemService(repository).findBy(1L, 1L);
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(item, found.get());
        verify(repository).findByCollectionIdAndId(1L, 1L);
    }

    @Test
    @ForMethod("findBy(Long collectionId, Long id)")
    public void shouldFindItemByCollectionIdAndId_whenItemDoesNotExist() {
        var repository = mock(ItemRepository.class);
        when(repository.findByCollectionIdAndId(1L, 1L)).thenReturn(Optional.empty());
        var found = new ItemService(repository).findBy(1L, 1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
        verify(repository).findByCollectionIdAndId(1L, 1L);
    }

    @Test
    @ForMethod("save(Item item)")
    public void shouldSaveItem() {
        var item = new Item(Map.of("key", "value"));
        var repository = mock(ItemRepository.class);
        when(repository.save(item)).then(invocation -> item);
        var saved = new ItemService(repository).save(item);
        assertNotNull(saved);
        assertEquals(item, saved);
    }

    @Test
    @ForMethod("delete(Long collectionId, Long id)")
    public void shouldDeleteItemByCollectionIdAndId() {
        var repository = mock(ItemRepository.class);
        doNothing().when(repository)
                .deleteByCollectionIdAndId(anyLong(), anyLong());
        new ItemService(repository).delete(1L, 1L);
        verify(repository).deleteByCollectionIdAndId(1L, 1L);
    }
}
