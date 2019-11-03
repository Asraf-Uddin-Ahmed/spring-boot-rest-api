package com.asraf.rsrc.dtos.mapper.persistence;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.dtos.mapper.StudentVerificationMapper;
import com.asraf.rsrc.dtos.request.entities.StudentVerificationRequestDto;
import com.asraf.rsrc.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.rsrc.entities.StudentVerification;

@Component
@Scope(value = "prototype")
public class StudentVerificationMapperImpl
		extends RequestResponseDtoMapperImpl<StudentVerification, StudentVerificationResponseDto, StudentVerificationRequestDto>
		implements StudentVerificationMapper {

	@Autowired
	public StudentVerificationMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, StudentVerificationResponseDto.class, StudentVerification.class);

		PropertyMap<StudentVerificationRequestDto, StudentVerification> requestToEntityPropertyMap = new PropertyMap<StudentVerificationRequestDto, StudentVerification>() {
			protected void configure() {
				map().getStudent().setId(source.getStudentId());
				// map().setCreationTime(new Date());
				// using(convertMassToLarge).map(source.getMass()).setLarge(false);
				
				// for mapping object field with different name
				// map(source.getUser(), destination.getCustomUser());
			}
		};

		PropertyMap<StudentVerification, StudentVerificationResponseDto> entityToResponsePropertyMap = new PropertyMap<StudentVerification, StudentVerificationResponseDto>() {
			protected void configure() {
				skip().getStudent().setStudentVerifications(null);
			}
		};

		super.setRequestToEntityPropertyMap(requestToEntityPropertyMap)
				.setEntityToResponsePropertyMap(entityToResponsePropertyMap);
	}

	public StudentVerification getEntity(StudentVerificationRequestDto requestDto) {
		StudentVerification studentVerification = super.getEntity(requestDto);
		studentVerification.setCreationTime(new Date());
		return studentVerification;
	}
	
	public void loadEntity(StudentVerificationRequestDto requestDto, StudentVerification entity) {
		entity.setStudent(null);
		super.loadEntity(requestDto, entity);
	}
}
