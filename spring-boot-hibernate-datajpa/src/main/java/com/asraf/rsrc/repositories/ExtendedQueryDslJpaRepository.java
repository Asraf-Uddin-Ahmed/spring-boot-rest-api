package com.asraf.rsrc.repositories;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.asraf.rsrc.constants.ColumnType;
import com.asraf.rsrc.entities.BaseEntity;
import com.querydsl.jpa.JPQLQuery;

@NoRepositoryBean
public interface ExtendedQueryDslJpaRepository<TEntity extends BaseEntity, ID extends Serializable>
		extends JpaRepository<TEntity, ID>, QuerydslPredicateExecutor<TEntity> {

	Page<TEntity> findAll(JPQLQuery<TEntity> jpqlQuery, Pageable pageable);

	Page<TEntity> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

	<TReturn> Page<TReturn> getListOfDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}