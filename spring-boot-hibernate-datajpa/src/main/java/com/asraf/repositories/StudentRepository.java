package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.Student;

@Transactional
public interface StudentRepository extends StudentRepositoryCrud, JpaSpecificationExecutor<Student>, StudentRepositoryExtended {

}
