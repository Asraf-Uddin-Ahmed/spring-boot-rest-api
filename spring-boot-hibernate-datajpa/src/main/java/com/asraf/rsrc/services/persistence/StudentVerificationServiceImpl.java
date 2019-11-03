package com.asraf.rsrc.services.persistence;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.StudentVerification;
import com.asraf.rsrc.repositories.StudentVerificationRepository;
import com.asraf.rsrc.rsql.CustomRsqlVisitor;
import com.asraf.rsrc.services.StudentVerificationService;
import com.asraf.rsrc.utils.ExceptionPreconditions;
import com.asraf.rsrc.utils.StringUtils;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@Service
@Transactional
public class StudentVerificationServiceImpl implements StudentVerificationService {

	private StudentVerificationRepository studentVerificationRepository;

	@Autowired
	public StudentVerificationServiceImpl(StudentVerificationRepository studentVerificationRepository) {
		this.studentVerificationRepository = studentVerificationRepository;
	}

	public StudentVerification save(StudentVerification studentVerification) {
		return studentVerificationRepository.save(studentVerification);
	}

	public void delete(StudentVerification studentVerification) {
		studentVerificationRepository.delete(studentVerification);
	}

	public StudentVerification getById(Long id) {
		try {
			return studentVerificationRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(StudentVerification.class, "id", id.toString());
		}
	}

	public Iterable<StudentVerification> getAll() {
		return studentVerificationRepository.findAll();
	}

	public Page<StudentVerification> getByQuery(String search, Pageable pageable) {
		if (StringUtils.isNullOrEmpty(search))
			return studentVerificationRepository.findAll(pageable);
		Node rootNode = new RSQLParser().parse(search);
		Specification<StudentVerification> spec = rootNode.accept(new CustomRsqlVisitor<StudentVerification>());
		Page<StudentVerification> studentVerification = studentVerificationRepository.findAll(spec, pageable);
		return studentVerification;
	}

}
