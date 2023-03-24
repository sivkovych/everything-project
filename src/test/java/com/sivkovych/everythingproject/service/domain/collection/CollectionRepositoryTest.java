package com.sivkovych.everythingproject.service.domain.collection;

import com.sivkovych.everythingproject._util.displayname.ForClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
@ForClass(CollectionRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CollectionRepositoryTest {}
