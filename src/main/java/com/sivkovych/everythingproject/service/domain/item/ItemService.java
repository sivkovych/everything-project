package com.sivkovych.everythingproject.service.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    public Optional<Item> findBy(Long collectionId, Long id) {
        return repository.findByCollectionIdAndId(collectionId, id);
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public void delete(Long collectionId, Long id) {
        repository.deleteByCollectionIdAndId(collectionId, id);
    }
}
