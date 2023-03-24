package com.sivkovych.everythingproject.service.domain.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.ObjectMapperConfiguration;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableJpaAuditing
@ForClass(ItemRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {
    private final ItemRepository repository;
    private final TestEntityManager entityManager;

    @Test
    @ForMethod("findByCollectionIdAndId(Long collectionId, Long id)")
    public void shouldFindItemByCollectionIdAndId() {
        var collection = getSavedCollection();
        var item = collection.getItems()
                .iterator()
                .next();
        var found = repository.findByCollectionIdAndId(collection.getId(), item.getId());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(item, found.get());
    }

    @Test
    @ForMethod("findByCollectionIdAndId(Long collectionId, Long id)")
    public void shouldFindItemByCollectionIdAndId_whenCollectionDoesNotExist() {
        var item = getSavedItem();
        var found = repository.findByCollectionIdAndId(123L, item.getId());
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @ForMethod("findByCollectionIdAndId(Long collectionId, Long id)")
    public void shouldFindItemByCollectionIdAndId_whenItemDoesNotExist() {
        var collection = getSavedCollection();
        var found = repository.findByCollectionIdAndId(collection.getId(), 123L);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @ForMethod("deleteByCollectionIdAndId(Long collectionId, Long id)")
    public void shouldDeleteItemByCollectionIdAndId() {
        var collection = getSavedCollection();
        var item = collection.getItems()
                .iterator()
                .next();
        repository.deleteByCollectionIdAndId(collection.getId(), item.getId());
        var found = entityManager.find(Item.class, item.getId());
        assertNull(found);
    }

    private Collection getSavedCollection() {
        var collection = entityManager.persist(Collection.builder()
                                                       .name("some-collection-name")
                                                       .build());
        ReflectionTestUtils.setField(collection, "items", Item.builder()
                .collectionId(collection.getId())
                .data(Map.of("key", "value"))
                .build()
                .as());
        return entityManager.persistAndFlush(collection);
    }

    private Item getSavedItem() {
        return getSavedCollection().getItems()
                .iterator()
                .next();
    }

    @TestConfiguration
    @RequiredArgsConstructor
    static class MapperConfiguration {
        @Bean
        public ObjectMapper globalMapper() {
            return ObjectMapperConfiguration.getGlobalMapper();
        }
    }
}
