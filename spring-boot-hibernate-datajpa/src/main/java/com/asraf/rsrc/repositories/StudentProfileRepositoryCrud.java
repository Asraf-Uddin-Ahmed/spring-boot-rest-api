package com.asraf.rsrc.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.StudentProfile;

@Transactional
public interface StudentProfileRepositoryCrud extends PagingAndSortingRepository<StudentProfile, Long> {

}
