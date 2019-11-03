package com.asraf.rsrc.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.rsrc.constants.ColumnType;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.models.search.StudentSearch;
import com.asraf.rsrc.models.search.extended.StudentWithVerificationSearch;

public interface StudentService {

	Student save(Student student);

	void delete(Student student);

	Student getById(Long id);

	Iterable<Student> getAll();

	Student getByEmail(String email);

	List<Student> getByNameContains(String name);

	List<Student> getBySearchCrud(StudentSearch searchItem);

	Page<Student> getBySearchCrudPageable(StudentSearch searchItem, Pageable pageable);

	Page<Student> getBySearchIntoJoiningTablePageable(StudentWithVerificationSearch searchItem, Pageable pageable);

	Page<Student> getByQuery(String search, Pageable pageable);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
