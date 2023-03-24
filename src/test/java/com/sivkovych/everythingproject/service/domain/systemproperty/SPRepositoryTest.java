package com.sivkovych.everythingproject.service.domain.systemproperty;

import com.sivkovych.everythingproject._util.displayname.ForClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ForClass(SPRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SPRepositoryTest {}
