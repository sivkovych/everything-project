package com.iseedead.everything_project.domain.system_property;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SPService {
    private final SPRepository repository;

    public Optional<SystemProperty> findBy(SPName key) {
        return repository.findById(key);
    }
}
