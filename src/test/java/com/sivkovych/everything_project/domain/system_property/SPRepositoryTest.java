package com.sivkovych.everything_project.domain.system_property;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(persisted, found.get());
    }

    @Test
    public void shouldNotFindItemById_whenItemDoesNotExist() {
        var found = repository.findById(SPName.NULL);
        assertNotNull(found);
        Assertions.assertTrue(found.isEmpty());
    }
}
