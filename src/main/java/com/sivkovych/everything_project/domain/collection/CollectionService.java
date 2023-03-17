package com.sivkovych.everything_project.domain.collection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository repository;

    public Optional<Collection> findBy(Long id) {
        return repository.findById(id);
    }

    public Collection save(Collection item) {
        return repository.save(item);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
