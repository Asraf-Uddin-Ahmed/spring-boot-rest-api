package com.asraf.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.StudentVerification;

@Transactional
public interface StudentVerificationRepositoryCrud extends PagingAndSortingRepository<StudentVerification, Long> {

}
