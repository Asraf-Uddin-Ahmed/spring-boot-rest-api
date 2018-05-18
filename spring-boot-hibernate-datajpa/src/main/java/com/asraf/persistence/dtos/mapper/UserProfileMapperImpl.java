package com.asraf.persistence.dtos.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.core.dtos.mapper.UserProfileMappper;
import com.asraf.core.dtos.request.UserProfileRequestDto;
import com.asraf.core.dtos.response.UserProfileResponseDto;
import com.asraf.core.entities.User;
import com.asraf.core.entities.UserProfile;

@Component
@Scope(value = "prototype")
public class UserProfileMapperImpl extends DtoMapperImpl<UserProfile, UserProfileRequestDto, UserProfileResponseDto>
		implements UserProfileMappper {

	@Autowired
	public UserProfileMapperImpl(ModelMapper modelMapper) {
		super(UserProfile.class, UserProfileResponseDto.class, modelMapper);

		PropertyMap<UserProfile, UserProfileResponseDto> entityToResponsePropertyMap = new PropertyMap<UserProfile, UserProfileResponseDto>() {
			protected void configure() {
				 skip().getUser().setUserVerifications(null);
			}
		};

		super.setEntityToResponsePropertyMap(entityToResponsePropertyMap);

	}

	public UserProfile getEntity(UserProfileRequestDto requestDto, User user) {
		UserProfile userProfile = super.getEntity(requestDto);
		userProfile.setUser(user);
		return userProfile;
	}
}
