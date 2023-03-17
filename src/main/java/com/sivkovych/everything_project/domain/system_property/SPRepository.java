package com.sivkovych.everything_project.domain.system_property;

import com.sivkovych.everything_project.domain.ReadOnlyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
interface SPRepository extends ReadOnlyRepository<SystemProperty, SPName> {}
