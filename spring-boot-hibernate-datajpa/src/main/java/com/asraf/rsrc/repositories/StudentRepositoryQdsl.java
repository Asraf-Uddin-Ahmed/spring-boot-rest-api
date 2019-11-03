package com.asraf.rsrc.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.Student;

@Transactional
public interface StudentRepositoryQdsl extends ExtendedQueryDslJpaRepository<Student, Long>  {

}
