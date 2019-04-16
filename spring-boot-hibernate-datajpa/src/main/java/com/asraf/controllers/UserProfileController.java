package com.asraf.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.UserProfileMapper;
import com.asraf.dtos.request.entities.UserProfileRequestDto;
import com.asraf.dtos.response.entities.UserProfileResponseDto;
import com.asraf.entities.UserEntity;
import com.asraf.entities.UserProfile;
import com.asraf.services.UserProfileService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("")
public class UserProfileController {

	private UserProfileService userProfileService;
	private UserService userService;
	private UserProfileMapper userProfileMappper;

	@Autowired
	public UserProfileController(UserProfileService userProfileService, UserService userService,
			UserProfileMapper userProfileMappper) {
		this.userProfileMappper = userProfileMappper;
		this.userService = userService;
		this.userProfileService = userProfileService;
	}

	@GetMapping("/user-profiles")
	public List<UserProfileResponseDto> getAll() {
		List<UserProfileResponseDto> response = userProfileMappper.getResponseDtos(this.userProfileService.getAll());
		return response;
	}

	@GetMapping("/user-profiles/{id}")
	public UserProfileResponseDto getById(@PathVariable long id) {
		UserProfile userProfile = userProfileService.getById(id);
		return userProfileMappper.getResponseDto(userProfile);
	}

	@PostMapping("users/{userId}/user-profiles")
	@ResponseStatus(HttpStatus.CREATED)
	public UserProfileResponseDto create(@PathVariable long userId,
			@Valid @RequestBody UserProfileRequestDto requestDto) {
		UserEntity user = this.userService.getById(userId);
		UserProfile userProfile = userProfileMappper.getEntity(requestDto, user);
		userProfileService.save(userProfile);
		return userProfileMappper.getResponseDto(userProfile);
	}

	@DeleteMapping("/user-profiles/{id}")
	public UserProfileResponseDto delete(@PathVariable long id) {
		UserProfile userProfile = userProfileService.getById(id);
		UserProfileResponseDto response = userProfileMappper.getResponseDto(userProfile);
		userProfileService.delete(userProfile);
		return response;
	}

	@PutMapping("/user-profiles/{id}")
	public UserProfileResponseDto update(@PathVariable long id, @Valid @RequestBody UserProfileRequestDto requestDto) {
		UserProfile userProfile = userProfileService.getById(id);
		userProfileMappper.loadEntity(requestDto, userProfile);
		userProfileService.save(userProfile);
		return userProfileMappper.getResponseDto(userProfile);
	}

}
