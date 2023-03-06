package com.iseedead.everything_project.domain.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {
    private final ItemRepository repository;
    private final TestEntityManager entityManager;

    @Test
    public void shouldFindItemById_whenItemExists() {
        var item = new Item();
        item.setData(Map.of("key", "value"));
        item.setCreatedAt(LocalDateTime.now());
        var persisted = entityManager.persistAndFlush(item);
        var found = repository.findById(persisted.getId());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(persisted, found.get());
    }

    @Test
    public void shouldNotFindItemById_whenItemDoesNotExist() {
        var found = repository.findById(1L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSaveItem() {
        var item = new Item();
        item.setData(Map.of("key", "value"));
        item.setCreatedAt(LocalDateTime.now());
        var saved = repository.save(item);
        assertNotNull(saved);
        assertEquals(item.getData(), saved.getData());
        var found = entityManager.find(Item.class, saved.getId());
        assertNotNull(found);
        assertEquals(found, saved);
    }

    @Test
    public void shouldDeleteItem() {
        var item = new Item();
        item.setData(Map.of("key", "value"));
        item.setCreatedAt(LocalDateTime.now());
        var persisted = entityManager.persistAndFlush(item);
        repository.deleteById(persisted.getId());
        var found = entityManager.find(Item.class, persisted.getId());
        assertNull(found);
    }
}
