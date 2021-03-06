package com.asraf.rsrc.services.persistence;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.StudentProfile;
import com.asraf.rsrc.repositories.StudentProfileRepository;
import com.asraf.rsrc.services.StudentProfileService;
import com.asraf.rsrc.utils.ExceptionPreconditions;

@Service
@Transactional
public class StudentProfileServiceImpl implements StudentProfileService {

	private StudentProfileRepository studentProfileRepository;

	@Autowired
	public StudentProfileServiceImpl(StudentProfileRepository studentProfileRepository) {
		this.studentProfileRepository = studentProfileRepository;
	}

	public StudentProfile save(StudentProfile studentProfile) {
		return studentProfileRepository.save(studentProfile);
	}

	public void delete(StudentProfile studentProfile) {
		studentProfileRepository.delete(studentProfile);
	}

	public StudentProfile getById(Long id) {
		try {
			return studentProfileRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(StudentProfile.class, "id", id.toString());
		}
	}

	public Iterable<StudentProfile> getAll() {
		return studentProfileRepository.findAll();
	}

}
