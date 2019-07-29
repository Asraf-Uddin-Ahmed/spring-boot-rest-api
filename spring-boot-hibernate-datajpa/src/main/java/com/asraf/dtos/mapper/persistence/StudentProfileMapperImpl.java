package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.StudentProfileMapper;
import com.asraf.dtos.request.entities.StudentProfileRequestDto;
import com.asraf.dtos.response.entities.StudentProfileResponseDto;
import com.asraf.entities.Student;
import com.asraf.entities.StudentProfile;

@Component
@Scope(value = "prototype")
public class StudentProfileMapperImpl
		extends RequestResponseDtoMapperImpl<StudentProfile, StudentProfileResponseDto, StudentProfileRequestDto>
		implements StudentProfileMapper {

	@Autowired
	public StudentProfileMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, StudentProfileResponseDto.class, StudentProfile.class);

		PropertyMap<StudentProfile, StudentProfileResponseDto> entityToResponsePropertyMap = new PropertyMap<StudentProfile, StudentProfileResponseDto>() {
			protected void configure() {
				skip().getStudent().setStudentVerifications(null);
			}
		};

		super.setEntityToResponsePropertyMap(entityToResponsePropertyMap);

	}

	public StudentProfile getEntity(StudentProfileRequestDto requestDto, Student student) {
		StudentProfile studentProfile = super.getEntity(requestDto);
		studentProfile.setStudent(student);
		return studentProfile;
	}
}
