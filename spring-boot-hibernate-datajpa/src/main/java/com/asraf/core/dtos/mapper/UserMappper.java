package com.asraf.core.dtos.mapper;

import java.util.List;

import com.asraf.core.dtos.request.UserRequestDto;
import com.asraf.core.dtos.response.UserResponseDto;
import com.asraf.core.entities.User;

public interface UserMappper {
	User getEntityForCreate(UserRequestDto requestDto);

	User getEntityForUpdate(Long id, UserRequestDto requestDto);

	UserResponseDto getResponseDto(User user);

	List<UserResponseDto> getResponseDtos(Iterable<User> entities);
}
