package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.UserEntity;

@Transactional
public interface UserRepository extends UserRepositoryCrud, JpaSpecificationExecutor<UserEntity>, UserRepositoryExtended {

}
