package com.asraf.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.constants.ColumnType;
import com.asraf.entities.UserEntity;
import com.asraf.models.search.UserSearch;
import com.asraf.models.search.extended.UserWithVerificationSearch;

public interface UserService {

	UserEntity save(UserEntity user);

	void delete(UserEntity user);

	UserEntity getById(Long id);

	Iterable<UserEntity> getAll();

	UserEntity getByEmail(String email);

	List<UserEntity> getByNameContains(String name);

	List<UserEntity> getBySearchCrud(UserSearch searchItem);

	Page<UserEntity> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable);

	Page<UserEntity> getBySearchIntoJoiningTablePageable(UserWithVerificationSearch searchItem, Pageable pageable);

	Page<UserEntity> getByQuery(String search, Pageable pageable);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
