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
import com.asraf.entities.UserEntity;
import com.asraf.models.search.UserSearch;
import com.asraf.models.search.extended.UserWithVerificationSearch;
import com.asraf.repositories.UserEntityRepository;
import com.asraf.rsql.CustomRsqlVisitor;
import com.asraf.services.UserService;
import com.asraf.utils.ExceptionPreconditions;
import com.asraf.utils.StringUtils;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserEntityRepository userEntityRepository;

	@Autowired
	public UserServiceImpl(UserEntityRepository userEntityRepository) {
		this.userEntityRepository = userEntityRepository;
	}

	public UserEntity save(UserEntity user) {
		return userEntityRepository.save(user);
	}

	public void delete(UserEntity user) {
		userEntityRepository.delete(user);
	}

	public UserEntity getById(Long id) {
		try {
			return userEntityRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(UserEntity.class, "id", id.toString());
		}
	}

	public Iterable<UserEntity> getAll() {
		return userEntityRepository.findAll();
	}

	public UserEntity getByEmail(String email) {
		UserEntity user = userEntityRepository.findByEmail(email);
		ExceptionPreconditions.checkFound(user, UserEntity.class, "email", email.toString());
		return user;
	}

	public List<UserEntity> getByNameContains(String name) {
		return userEntityRepository.findByNameContains(name);
	}

	public List<UserEntity> getBySearchCrud(UserSearch searchItem) {
		return userEntityRepository.findByNameOrEmail(searchItem.getName(), searchItem.getEmail());
	}

	public Page<UserEntity> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable) {
		return userEntityRepository.findByNameContainsOrEmailContainsAllIgnoreCase(searchItem.getName(),
				searchItem.getEmail(), pageable);
	}

	public Page<UserEntity> getBySearchIntoJoiningTablePageable(UserWithVerificationSearch searchItem, Pageable pageable) {
		return userEntityRepository.GetByUserWithVerificationSeach(searchItem, pageable);
	}

	public Page<UserEntity> getByQuery(String search, Pageable pageable) {
		if (StringUtils.isNullOrEmpty(search))
			return userEntityRepository.findAll(pageable);
		Node rootNode = new RSQLParser().parse(search);
		Specification<UserEntity> spec = rootNode.accept(new CustomRsqlVisitor<UserEntity>());
		Page<UserEntity> users = userEntityRepository.findAll(spec, pageable);
		return users;
	}

	public Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable) {
		return userEntityRepository.getByDistinctColumn(columnName, columnType, pageable);
	}

}
