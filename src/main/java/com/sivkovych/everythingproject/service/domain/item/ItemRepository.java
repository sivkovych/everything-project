package com.sivkovych.everythingproject.service.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByCollectionIdAndId(Long collectionId, Long id);

    void deleteByCollectionIdAndId(Long collectionId, Long id);
}
