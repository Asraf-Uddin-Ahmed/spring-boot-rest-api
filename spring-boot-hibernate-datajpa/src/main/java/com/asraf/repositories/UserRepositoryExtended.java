package com.asraf.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.constants.ColumnType;
import com.asraf.entities.UserEntity;
import com.asraf.models.search.extended.UserWithVerificationSearch;

@Transactional
public interface UserRepositoryExtended {

	Page<UserEntity> GetByUserWithVerificationSeach(UserWithVerificationSearch searchItem, Pageable pageable);

	List<UserEntity> getByName(UserWithVerificationSearch searchItem);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
