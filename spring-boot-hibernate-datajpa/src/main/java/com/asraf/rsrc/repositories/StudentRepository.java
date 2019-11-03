package com.asraf.rsrc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.rsrc.entities.Student;

@Transactional
public interface StudentRepository extends StudentRepositoryCrud, JpaSpecificationExecutor<Student>, StudentRepositoryExtended {

}
