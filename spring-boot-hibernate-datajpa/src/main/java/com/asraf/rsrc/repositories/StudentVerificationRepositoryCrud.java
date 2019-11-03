package com.asraf.rsrc.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.StudentVerification;

@Transactional
public interface StudentVerificationRepositoryCrud extends PagingAndSortingRepository<StudentVerification, Long> {

}
