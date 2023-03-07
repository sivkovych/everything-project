package com.iseedead.everything_project.domain.system_property;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SPRepositoryTest {
    private final SPRepository repository;
    private final TestEntityManager entityManager;

    @Test
    public void shouldFindItemById_whenItemExists() {
        var property = SystemProperty.builder()
                .name(SPName.NULL)
                .build();
        var persisted = entityManager.persistAndFlush(property);
        var found = repository.findById(persisted.getName());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(persisted, found.get());
    }

    @Test
    public void shouldNotFindItemById_whenItemDoesNotExist() {
        var found = repository.findById(SPName.NULL);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }
}
