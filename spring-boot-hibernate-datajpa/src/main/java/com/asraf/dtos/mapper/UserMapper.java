package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.UserEntity;

public interface UserMapper extends RequestResponseDtoMapper<UserEntity, UserResponseDto, UserRequestDto> {

}
