package com.asraf.rsrc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.constants.ColumnType;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.models.search.extended.StudentWithVerificationSearch;

@Transactional
public interface StudentRepositoryExtended {

	Page<Student> GetByStudentWithVerificationSeach(StudentWithVerificationSearch searchItem, Pageable pageable);

	List<Student> getByName(StudentWithVerificationSearch searchItem);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
