package com.asraf.rsrc.services;

import com.asraf.rsrc.entities.StudentProfile;

public interface StudentProfileService {

	StudentProfile save(StudentProfile studentProfile);

	void delete(StudentProfile studentProfile);

	StudentProfile getById(Long id);

	Iterable<StudentProfile> getAll();

}
