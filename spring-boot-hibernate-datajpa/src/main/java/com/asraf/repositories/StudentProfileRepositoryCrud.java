package com.asraf.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.StudentProfile;

@Transactional
public interface StudentProfileRepositoryCrud extends PagingAndSortingRepository<StudentProfile, Long> {

}
