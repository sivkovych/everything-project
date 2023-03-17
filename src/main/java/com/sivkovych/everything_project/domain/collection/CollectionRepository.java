package com.sivkovych.everything_project.domain.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CollectionRepository extends JpaRepository<Collection, Long> {}
