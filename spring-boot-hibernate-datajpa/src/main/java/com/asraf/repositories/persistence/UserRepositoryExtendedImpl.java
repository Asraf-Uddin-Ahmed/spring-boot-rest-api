package com.asraf.repositories.persistence;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.asraf.constants.ColumnType;
import com.asraf.entities.QUserEntity;
import com.asraf.entities.QUserVerification;
import com.asraf.entities.UserEntity;
import com.asraf.models.search.extended.UserWithVerificationSearch;
import com.asraf.repositories.UserRepositoryExtended;
import com.asraf.repositories.UserRepositoryQdsl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class UserRepositoryExtendedImpl implements UserRepositoryExtended {

	@PersistenceContext
	private EntityManager entityManager;

	private UserRepositoryQdsl userRepositoryQdsl;

	@Autowired
	public UserRepositoryExtendedImpl(EntityManager entityManager, UserRepositoryQdsl userRepositoryQdsl) {
		this.entityManager = entityManager;
		this.userRepositoryQdsl = userRepositoryQdsl;
	}

	public Page<UserEntity> GetByUserWithVerificationSeach(UserWithVerificationSearch searchItem, Pageable pageable) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		QUserVerification qUserVerification = QUserVerification.userVerification;

		JPQLQuery<UserEntity> query = new JPAQuery<>(entityManager);

		BooleanBuilder predicate = new BooleanBuilder();
		if (searchItem.getName() != null)
			predicate.and(qUserEntity.name.eq(searchItem.getName()));
		if (searchItem.getEmail() != null)
			predicate.or(qUserEntity.email.eq(searchItem.getEmail()));
		if (searchItem.getCreationTime() != null) {
			Date date = Date.from(searchItem.getCreationTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
			predicate.and(qUserVerification.creationTime.after(date));
		}

		query.from(qUserEntity).join(qUserEntity.userVerifications, qUserVerification).where(predicate).distinct();

		return userRepositoryQdsl.findAll(query, pageable);
	}

	public List<UserEntity> getByName(UserWithVerificationSearch searchItem) {
		QUserEntity qUserEntity = QUserEntity.userEntity;
		JPQLQuery<UserEntity> query = new JPAQuery<>(entityManager);
		return query.from(qUserEntity).where(qUserEntity.name.eq(searchItem.getName())).fetch();
	}

	public Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable) {
		return userRepositoryQdsl.getListOfDistinctColumn(columnName, columnType, pageable);
	}

}
