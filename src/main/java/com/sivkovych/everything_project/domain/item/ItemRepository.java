package com.sivkovych.everything_project.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ItemRepository extends JpaRepository<Item, Long> {}
