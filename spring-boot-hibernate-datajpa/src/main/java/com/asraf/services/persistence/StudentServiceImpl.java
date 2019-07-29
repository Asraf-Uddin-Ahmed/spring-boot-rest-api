package com.asraf.services.persistence;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.constants.ColumnType;
import com.asraf.entities.Student;
import com.asraf.models.search.StudentSearch;
import com.asraf.models.search.extended.StudentWithVerificationSearch;
import com.asraf.repositories.StudentRepository;
import com.asraf.rsql.CustomRsqlVisitor;
import com.asraf.services.StudentService;
import com.asraf.utils.ExceptionPreconditions;
import com.asraf.utils.StringUtils;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student save(Student student) {
		return studentRepository.save(student);
	}

	public void delete(Student student) {
		studentRepository.delete(student);
	}

	public Student getById(Long id) {
		try {
			return studentRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(Student.class, "id", id.toString());
		}
	}

	public Iterable<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student getByEmail(String email) {
		Student student = studentRepository.findByEmail(email);
		ExceptionPreconditions.checkFound(student, Student.class, "email", email.toString());
		return student;
	}

	public List<Student> getByNameContains(String name) {
		return studentRepository.findByNameContains(name);
	}

	public List<Student> getBySearchCrud(StudentSearch searchItem) {
		return studentRepository.findByNameOrEmail(searchItem.getName(), searchItem.getEmail());
	}

	public Page<Student> getBySearchCrudPageable(StudentSearch searchItem, Pageable pageable) {
		return studentRepository.findByNameContainsOrEmailContainsAllIgnoreCase(searchItem.getName(),
				searchItem.getEmail(), pageable);
	}

	public Page<Student> getBySearchIntoJoiningTablePageable(StudentWithVerificationSearch searchItem, Pageable pageable) {
		return studentRepository.GetByStudentWithVerificationSeach(searchItem, pageable);
	}

	public Page<Student> getByQuery(String search, Pageable pageable) {
		if (StringUtils.isNullOrEmpty(search))
			return studentRepository.findAll(pageable);
		Node rootNode = new RSQLParser().parse(search);
		Specification<Student> spec = rootNode.accept(new CustomRsqlVisitor<Student>());
		Page<Student> students = studentRepository.findAll(spec, pageable);
		return students;
	}

	public Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable) {
		return studentRepository.getByDistinctColumn(columnName, columnType, pageable);
	}

}
