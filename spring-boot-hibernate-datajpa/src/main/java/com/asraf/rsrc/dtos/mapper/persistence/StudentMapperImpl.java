package com.asraf.rsrc.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.dtos.mapper.StudentMapper;
import com.asraf.rsrc.dtos.request.entities.StudentRequestDto;
import com.asraf.rsrc.dtos.response.entities.StudentResponseDto;
import com.asraf.rsrc.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.entities.StudentVerification;

@Component
@Scope(value = "prototype")
public class StudentMapperImpl extends RequestResponseDtoMapperImpl<Student, StudentResponseDto, StudentRequestDto> implements StudentMapper {

	@Autowired
	public StudentMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, StudentResponseDto.class, Student.class);
		
		PropertyMap<StudentVerification, StudentVerificationResponseDto> studentVerificationEntityToResponsePropertyMap = new PropertyMap<StudentVerification, StudentVerificationResponseDto>() {
			protected void configure() {
				skip().setStudent(null);
			}
		};

		super.setNestedObjectPropertyMap(studentVerificationEntityToResponsePropertyMap);

	}

}
