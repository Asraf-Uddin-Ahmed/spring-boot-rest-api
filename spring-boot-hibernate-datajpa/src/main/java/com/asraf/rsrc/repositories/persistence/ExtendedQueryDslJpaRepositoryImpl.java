package com.asraf.rsrc.repositories.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;

import com.asraf.rsrc.constants.ColumnType;
import com.asraf.rsrc.entities.BaseEntity;
import com.asraf.rsrc.repositories.ExtendedQueryDslJpaRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ExtendedQueryDslJpaRepositoryImpl<TEntity extends BaseEntity, ID extends Serializable>
		extends QuerydslJpaRepository<TEntity, ID> implements ExtendedQueryDslJpaRepository<TEntity, ID> {

	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

	private final EntityPath<TEntity> path;
	private final PathBuilder<TEntity> builder;
	private final Querydsl querydsl;

	private EntityManager entityManager;

	public ExtendedQueryDslJpaRepositoryImpl(JpaEntityInformation<TEntity, ID> entityInformation,
			EntityManager entityManager) {
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExtendedQueryDslJpaRepositoryImpl(JpaEntityInformation<TEntity, ID> entityInformation,
			EntityManager entityManager, EntityPathResolver resolver) {

		super(entityInformation, entityManager);
		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder(this.path.getType(), this.path.getMetadata());
		this.querydsl = new Querydsl(entityManager, this.builder);
		this.entityManager = entityManager;
	}

	@Override
	public Page<TEntity> findAll(JPQLQuery<TEntity> jpqlQuery, Pageable pageable) {

		final JPQLQuery<?> countQuery = jpqlQuery;

		JPQLQuery<TEntity> query = (JPQLQuery<TEntity>) querydsl.applyPagination(pageable, jpqlQuery);

		return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
	}

	public Page<TEntity> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable) {
		JPQLQuery<TEntity> countQuery = this.getCountQuery(columnName, columnType);
		JPQLQuery<TEntity> query = (JPQLQuery<TEntity>) querydsl.applyPagination(pageable, countQuery);
		return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
	}

	@SuppressWarnings("unchecked")
	public <TReturn> Page<TReturn> getListOfDistinctColumn(String columnName, ColumnType columnType,
			Pageable pageable) {
		Page<TEntity> pagedEntity = this.getByDistinctColumn(columnName, columnType, pageable);
		String methodName = "get" + org.apache.commons.lang3.StringUtils.capitalize(columnName);
		Collection<TReturn> content = CollectionUtils.collect(pagedEntity.getContent(),
				TransformerUtils.invokerTransformer(methodName));
		return PageableExecutionUtils.getPage(new ArrayList<TReturn>(content), pageable,
				this.getCountQuery(columnName, columnType)::fetchCount);
	}

	private JPQLQuery<TEntity> getCountQuery(String columnName, ColumnType columnType) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		return queryFactory.select(Projections.bean(this.path, Expressions.path(columnType.getTypeClass(), columnName)))
				.distinct().from(this.path);
	}

}