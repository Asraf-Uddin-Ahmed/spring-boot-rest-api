package com.asraf.rsrc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.rsrc.entities.StudentVerification;

@Transactional
public interface StudentVerificationRepository
		extends StudentVerificationRepositoryCrud, JpaSpecificationExecutor<StudentVerification> {

}
