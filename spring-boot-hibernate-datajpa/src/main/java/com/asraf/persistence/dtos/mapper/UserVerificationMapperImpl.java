package com.asraf.persistence.dtos.mapper;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asraf.core.dtos.mapper.UserVerificationMappper;
import com.asraf.core.dtos.request.UserVerificationRequestDto;
import com.asraf.core.dtos.response.UserVerificationResponseDto;
import com.asraf.core.entities.UserVerification;

@Service
public class UserVerificationMapperImpl
		extends DtoMapperImpl<UserVerification, UserVerificationRequestDto, UserVerificationResponseDto>
		implements UserVerificationMappper {

	@Autowired
	public UserVerificationMapperImpl(ModelMapper modelMapper) {
		super(UserVerification.class, UserVerificationResponseDto.class, modelMapper);
	
		super.requestToEntityPropertyMap = new PropertyMap<UserVerificationRequestDto, UserVerification>() {
			protected void configure() {
				map().getUser().setId(source.getUserId());
				// map().setCreationTime(new Date());
				// using(convertMassToLarge).map(source.getMass()).setLarge(false);
			}
		};
		
	}
	
	public UserVerification getEntity(UserVerificationRequestDto requestDto) {
		UserVerification userVerification = super.getEntity(requestDto);
		userVerification.setCreationTime(new Date());
		return userVerification;
	}
}
