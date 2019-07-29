package com.asraf.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.Student;

@Transactional
public interface StudentRepositoryQdsl extends ExtendedQueryDslJpaRepository<Student, Long>  {

}
