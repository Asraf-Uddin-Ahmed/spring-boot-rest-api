package com.asraf.services.persistence;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserVerification;
import com.asraf.repositories.UserVerificationRepository;
import com.asraf.services.UserVerificationService;
import com.asraf.util.ExceptionPreconditions;

@Service
@Transactional
public class UserVerificationServiceImpl implements UserVerificationService {

	private UserVerificationRepository userVerificationRepository;

	@Autowired
	public UserVerificationServiceImpl(UserVerificationRepository userVerificationRepository) {
		this.userVerificationRepository = userVerificationRepository;
	}

	public UserVerification save(UserVerification userVerification) {
		return userVerificationRepository.save(userVerification);
	}

	public void delete(UserVerification userVerification) {
		userVerificationRepository.delete(userVerification);
	}

	public UserVerification getById(Long id) {
		try {
			return userVerificationRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(UserVerification.class, "id", id.toString());
		}
	}

	public Iterable<UserVerification> getAll() {
		return userVerificationRepository.findAll();
	}

}
