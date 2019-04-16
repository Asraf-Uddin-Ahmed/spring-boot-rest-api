package com.asraf.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserEntity;

@Transactional
public interface UserEntityRepositoryCrud extends PagingAndSortingRepository<UserEntity, Long> {

	/**
	 * Retrieves an user by its email.
	 * 
	 * @param email
	 * @return The user having the passed email or null if no user is found
	 */
	public UserEntity findByEmail(String email);

//	@Query("select u from User u where u.name like %?1% order by name")
	List<UserEntity> findByNameContains(String name);

	List<UserEntity> findByNameOrEmail(String name, String email);

	// Slice<User> findAll(Pageable pageRequest);
	Page<UserEntity> findByNameContainsOrEmailContainsAllIgnoreCase(String name, String email, Pageable pageRequest);

	// @Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE
	// LOWER(CONCAT('%',:searchTerm, '%')) OR "
	// + "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	// List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable
	// pageRequest);

}
