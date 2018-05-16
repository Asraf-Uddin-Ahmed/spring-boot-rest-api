package com.asraf.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.core.dtos.mapper.UserMappper;
import com.asraf.core.dtos.request.UserRequestDto;
import com.asraf.core.dtos.response.UserResponseDto;
import com.asraf.core.entities.User;
import com.asraf.core.models.search.UserSearch;
import com.asraf.core.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMappper userMappper;

	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<UserResponseDto>> getAll() {
		List<UserResponseDto> response = userMappper.getResponseDtos(this.userService.getAll());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-by-email/{email}")
	@ResponseBody
	public ResponseEntity<UserResponseDto> getByEmail(@PathVariable String email) {
		try {
			User user = userService.getByEmail(email);
			return ResponseEntity.ok(userMappper.getResponseDto(user));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/get-by-name/{name}")
	@ResponseBody
	public ResponseEntity<List<UserResponseDto>> getByName(@PathVariable String name) {
		try {
			List<User> users = userService.getByNameContains(name);
			return ResponseEntity.ok(userMappper.getResponseDtos(users));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search-crud")
	@ResponseBody
	public ResponseEntity<List<UserResponseDto>> getBySearch(UserSearch searchItem) {
		try {
			List<User> users = userService.getBySearchCrud(searchItem);
			return ResponseEntity.ok(userMappper.getResponseDtos(users));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody UserRequestDto requestDto) {
		try {
			User user = userMappper.getEntity(requestDto);
			userService.save(user);
			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body("Error creating the user: " + ex.toString());
		}
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Object> delete(@PathVariable long id) {
		try {
			User user = userService.getById(id);
			userService.delete(user);
			return ResponseEntity.ok(user);
		} catch (NoSuchElementException nseex) {
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting the user: " + ex.toString());
		}
	}

	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Object> update(@PathVariable long id, @RequestBody UserRequestDto requestDto) {
		try {
			User user = userService.getById(id);
			userMappper.loadEntity(requestDto, user);
			userService.save(user);
			return ResponseEntity.ok(user);
		} catch (NoSuchElementException nseex) {
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating the user: " + ex.toString());
		}
	}

}
