package com.asraf.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserEntity;

@Transactional
public interface UserRepositoryQdsl extends ExtendedQueryDslJpaRepository<UserEntity, Long>  {

}
