package com.sivkovych.everythingproject.service.domain.systemproperty;

import com.sivkovych.everythingproject.service.domain.ReadOnlyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
interface SPRepository extends ReadOnlyRepository<SystemProperty, SPName> {}
