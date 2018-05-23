package com.asraf.services.persistence;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.User;
import com.asraf.exceptions.EntityNotFoundException;
import com.asraf.models.search.UserSearch;
import com.asraf.repositories.UserRepository;
import com.asraf.repositories.persistence.GenericSpecificationsBuilder;
import com.asraf.repositories.persistence.UserSpecification;
import com.asraf.services.UserService;
import com.asraf.util.CriteriaParser;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public User getById(Long id) throws EntityNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			throw new EntityNotFoundException(User.class, "id", id.toString());
		}
	}

	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	public User getByEmail(String email) throws EntityNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException(User.class, "email", email.toString());
		}
		return user;
	}

	public List<User> getByNameContains(String name) {
		return userRepository.findByNameContains(name);
	}

	public List<User> getBySearchCrud(UserSearch searchItem) {
		return userRepository.findByNameOrEmail(searchItem.getName(), searchItem.getEmail());
	}

	public Page<User> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable) {
		return userRepository.findByNameContainsOrEmailContainsAllIgnoreCase(searchItem.getName(),
				searchItem.getEmail(), pageable);
	}

	// TODO: implement pageable
	public Iterable<User> getByQuery(String search) {
		Specification<User> spec = resolveSpecificationFromInfixExpr(search);
		Iterable<User> users = userRepository.findAll(spec);
		return users;
	}

	// TODO: send it to different class
	protected Specification<User> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), UserSpecification::new);
    }
	
}
