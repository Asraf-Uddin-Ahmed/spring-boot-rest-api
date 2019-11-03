package com.asraf.rsrc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.rsrc.entities.Student;

@Transactional
public interface StudentRepositoryCrud extends PagingAndSortingRepository<Student, Long> {

	/**
	 * Retrieves an student by its email.
	 * 
	 * @param email
	 * @return The student having the passed email or null if no student is found
	 */
	public Student findByEmail(String email);

//	@Query("select u from Student u where u.name like %?1% order by name")
	List<Student> findByNameContains(String name);

	List<Student> findByNameOrEmail(String name, String email);

	// Slice<Student> findAll(Pageable pageRequest);
	Page<Student> findByNameContainsOrEmailContainsAllIgnoreCase(String name, String email, Pageable pageRequest);

	// @Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE
	// LOWER(CONCAT('%',:searchTerm, '%')) OR "
	// + "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	// List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable
	// pageRequest);

}
