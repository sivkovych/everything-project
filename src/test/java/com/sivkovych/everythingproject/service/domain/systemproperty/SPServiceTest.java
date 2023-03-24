package com.sivkovych.everythingproject.service.domain.systemproperty;

import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ForClass(SPService.class)
class SPServiceTest {
    @Test
    @ForMethod("findBy(SPName key)")
    public void shouldReturnSP_whenExists() {
        var property = SystemProperty.builder()
                .name(SPName.NULL)
                .build();
        var repository = mock(SPRepository.class);
        when(repository.findById(SPName.NULL)).thenReturn(Optional.of(property));
        var found = new SPService(repository).findBy(SPName.NULL);
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(property, found.get());
    }

    @Test
    @ForMethod("findBy(SPName key)")
    public void shouldReturnEmpty_whenDoesNotExist() {
        var repository = mock(SPRepository.class);
        when(repository.findById(any())).thenReturn(Optional.empty());
        var found = new SPService(repository).findBy(SPName.NULL);
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }
}
