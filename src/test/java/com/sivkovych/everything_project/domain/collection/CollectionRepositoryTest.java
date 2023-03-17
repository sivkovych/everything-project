package com.sivkovych.everything_project.domain.collection;

import com.sivkovych.everything_project.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CollectionRepositoryTest {
    private final CollectionRepository repository;
    private final TestEntityManager entityManager;

    @Test
    public void shouldFindEntityById_whenEntityExists() {
        var collection = getSavedCollection();
        var found = repository.findById(collection.getId());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(collection, found.get());
    }

    @Test
    public void shouldNotFindEntityById_whenEntityDoesNotExist() {
        var found = repository.findById(1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSaveEntity() {
        var collection = getSavedCollection().add(Item.builder()
                                                          .data(Map.of("k", "v"))
                                                          .build());
        var saved = repository.save(collection);
        assertNotNull(saved);
        assertIterableEquals(collection.getItems(), saved.getItems());
        var found = entityManager.find(Collection.class, saved.getId());
        assertNotNull(found);
        assertEquals(found, saved);
    }

    @Test
    public void shouldDeleteEntity() {
        var collection = getSavedCollection();
        repository.deleteById(collection.getId());
        var found = entityManager.find(Collection.class, collection.getId());
        assertNull(found);
    }

    private Collection getSavedCollection() {
        var item = Item.builder()
                .data(Map.of("key", "value"))
                .build();
        var collection = Collection.builder()
                .items(new HashSet<>() {{
                    add(item);
                }})
                .build();
        return entityManager.persistAndFlush(collection);
    }
}
