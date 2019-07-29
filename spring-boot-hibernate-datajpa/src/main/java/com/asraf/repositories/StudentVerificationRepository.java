package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.StudentVerification;

@Transactional
public interface StudentVerificationRepository
		extends StudentVerificationRepositoryCrud, JpaSpecificationExecutor<StudentVerification> {

}
