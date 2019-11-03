package com.asraf.rsrc.repositories.persistence;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.asraf.rsrc.constants.ColumnType;
import com.asraf.rsrc.entities.QStudent;
import com.asraf.rsrc.entities.QStudentVerification;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.models.search.extended.StudentWithVerificationSearch;
import com.asraf.rsrc.repositories.StudentRepositoryExtended;
import com.asraf.rsrc.repositories.StudentRepositoryQdsl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class StudentRepositoryExtendedImpl implements StudentRepositoryExtended {

	@PersistenceContext
	private EntityManager entityManager;

	private StudentRepositoryQdsl studentRepositoryQdsl;

	@Autowired
	public StudentRepositoryExtendedImpl(EntityManager entityManager, StudentRepositoryQdsl studentRepositoryQdsl) {
		this.entityManager = entityManager;
		this.studentRepositoryQdsl = studentRepositoryQdsl;
	}

	public Page<Student> GetByStudentWithVerificationSeach(StudentWithVerificationSearch searchItem, Pageable pageable) {
		QStudent qStudent = QStudent.student;
		QStudentVerification qStudentVerification = QStudentVerification.studentVerification;

		JPQLQuery<Student> query = new JPAQuery<>(entityManager);

		BooleanBuilder predicate = new BooleanBuilder();
		if (searchItem.getName() != null)
			predicate.and(qStudent.name.eq(searchItem.getName()));
		if (searchItem.getEmail() != null)
			predicate.or(qStudent.email.eq(searchItem.getEmail()));
		if (searchItem.getCreationTime() != null) {
			Date date = Date.from(searchItem.getCreationTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
			predicate.and(qStudentVerification.creationTime.after(date));
		}

		query.from(qStudent).join(qStudent.studentVerifications, qStudentVerification).where(predicate).distinct();

		return studentRepositoryQdsl.findAll(query, pageable);
	}

	public List<Student> getByName(StudentWithVerificationSearch searchItem) {
		QStudent qStudent = QStudent.student;
		JPQLQuery<Student> query = new JPAQuery<>(entityManager);
		return query.from(qStudent).where(qStudent.name.eq(searchItem.getName())).fetch();
	}

	public Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable) {
		return studentRepositoryQdsl.getListOfDistinctColumn(columnName, columnType, pageable);
	}

}
