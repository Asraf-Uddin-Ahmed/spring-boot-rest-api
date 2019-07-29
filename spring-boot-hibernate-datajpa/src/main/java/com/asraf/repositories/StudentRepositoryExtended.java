package com.asraf.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.constants.ColumnType;
import com.asraf.entities.Student;
import com.asraf.models.search.extended.StudentWithVerificationSearch;

@Transactional
public interface StudentRepositoryExtended {

	Page<Student> GetByStudentWithVerificationSeach(StudentWithVerificationSearch searchItem, Pageable pageable);

	List<Student> getByName(StudentWithVerificationSearch searchItem);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
