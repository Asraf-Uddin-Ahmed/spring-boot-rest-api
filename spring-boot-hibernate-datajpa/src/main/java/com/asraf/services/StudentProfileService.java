package com.asraf.services;

import com.asraf.entities.StudentProfile;

public interface StudentProfileService {

	StudentProfile save(StudentProfile studentProfile);

	void delete(StudentProfile studentProfile);

	StudentProfile getById(Long id);

	Iterable<StudentProfile> getAll();

}
