package com.sivkovych.everythingproject.service.domain.collection;

import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
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

@ForClass(CollectionService.class)
class CollectionServiceTest {
    @Test
    @ForMethod("findBy(Long id)")
    public void shouldReturnEntity_whenExists() {
        var collection = new Collection(new HashSet<>() {{
            add(new Item(Map.of("key", "value")));
        }});
        var repository = mock(CollectionRepository.class);
        when(repository.findById(1L)).thenReturn(Optional.of(collection));
        var found = new CollectionService(repository).findBy(1L);
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(collection, found.get());
    }

    @Test
    @ForMethod("findBy(Long id)")
    public void shouldReturnEmpty_whenEntityDoesNotExist() {
        var repository = mock(CollectionRepository.class);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        var found = new CollectionService(repository).findBy(1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @ForMethod("save(Collection collection)")
    public void shouldSaveEntity() {
        var collection = new Collection(new HashSet<>() {{
            add(new Item(Map.of("key", "value")));
        }});
        var repository = mock(CollectionRepository.class);
        when(repository.save(collection)).then(invocation -> collection);
        var saved = new CollectionService(repository).save(collection);
        assertNotNull(saved);
        assertEquals(collection, saved);
    }

    @Test
    @ForMethod("delete(Long id)")
    public void shouldDeleteEntity() {
        var repository = mock(CollectionRepository.class);
        doNothing().when(repository)
                .deleteById(anyLong());
        new CollectionService(repository).delete(1L);
        verify(repository).deleteById(1L);
    }
}
