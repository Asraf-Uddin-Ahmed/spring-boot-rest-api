package com.asraf.rsrc.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.rsrc.entities.StudentVerification;

public interface StudentVerificationService {

	StudentVerification save(StudentVerification studentVerification);

	void delete(StudentVerification studentVerification);

	StudentVerification getById(Long id);

	Iterable<StudentVerification> getAll();

	Page<StudentVerification> getByQuery(String search, Pageable pageable);

}
