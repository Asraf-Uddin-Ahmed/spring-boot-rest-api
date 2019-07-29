package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.StudentProfileRequestDto;
import com.asraf.dtos.response.entities.StudentProfileResponseDto;
import com.asraf.entities.Student;
import com.asraf.entities.StudentProfile;

public interface StudentProfileMapper
		extends RequestResponseDtoMapper<StudentProfile, StudentProfileResponseDto, StudentProfileRequestDto> {
	StudentProfile getEntity(StudentProfileRequestDto requestDto, Student user);
}
