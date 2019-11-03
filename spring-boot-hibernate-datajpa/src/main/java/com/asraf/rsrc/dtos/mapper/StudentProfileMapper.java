package com.asraf.rsrc.dtos.mapper;

import com.asraf.rsrc.dtos.request.entities.StudentProfileRequestDto;
import com.asraf.rsrc.dtos.response.entities.StudentProfileResponseDto;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.entities.StudentProfile;

public interface StudentProfileMapper
		extends RequestResponseDtoMapper<StudentProfile, StudentProfileResponseDto, StudentProfileRequestDto> {
	StudentProfile getEntity(StudentProfileRequestDto requestDto, Student user);
}
