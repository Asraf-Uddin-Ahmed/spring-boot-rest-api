package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.UserProfileMapper;
import com.asraf.dtos.request.entities.UserProfileRequestDto;
import com.asraf.dtos.response.entities.UserProfileResponseDto;
import com.asraf.entities.UserEntity;
import com.asraf.entities.UserProfile;

@Component
@Scope(value = "prototype")
public class UserProfileMapperImpl
		extends RequestResponseDtoMapperImpl<UserProfile, UserProfileResponseDto, UserProfileRequestDto>
		implements UserProfileMapper {

	@Autowired
	public UserProfileMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, UserProfileResponseDto.class, UserProfile.class);

		PropertyMap<UserProfile, UserProfileResponseDto> entityToResponsePropertyMap = new PropertyMap<UserProfile, UserProfileResponseDto>() {
			protected void configure() {
				skip().getUser().setUserVerifications(null);
			}
		};

		super.setEntityToResponsePropertyMap(entityToResponsePropertyMap);

	}

	public UserProfile getEntity(UserProfileRequestDto requestDto, UserEntity user) {
		UserProfile userProfile = super.getEntity(requestDto);
		userProfile.setUser(user);
		return userProfile;
	}
}
