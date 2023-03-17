package com.sivkovych.everything_project.domain.item;

import com.sivkovych.everything_project.domain.collection.Collection;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {
    private final ItemRepository repository;
    private final TestEntityManager entityManager;

    @Test
    public void shouldFindEntityById_whenEntityExists() {
        var item = getSavedItem();
        var found = repository.findById(item.getId());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(item, found.get());
    }

    @Test
    public void shouldNotFindEntityById_whenEntityDoesNotExist() {
        var found = repository.findById(1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSaveEntity() {
        var item = getSavedItem();
        item.setData(Map.of("new-key", "new-value"));
        var saved = repository.save(item);
        assertNotNull(saved);
        assertEquals(item.getData(), saved.getData());
        var found = entityManager.find(Item.class, saved.getId());
        assertNotNull(found);
        assertEquals(found, saved);
    }

    @Test
    public void shouldDeleteEntity() {
        var item = getSavedItem();
        repository.deleteById(item.getId());
        var found = entityManager.find(Item.class, item.getId());
        assertNull(found);
    }

    private Item getSavedItem() {
        var item = new Item(Map.of("key", "value"));
        var collection = new Collection(new HashSet<>() {{
            add(item);
        }});
        return entityManager.persistAndFlush(collection)
                .getItems()
                .iterator()
                .next();
    }
}
