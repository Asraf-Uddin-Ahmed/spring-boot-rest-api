package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.StudentVerificationRequestDto;
import com.asraf.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.entities.StudentVerification;

public interface StudentVerificationMapper
		extends RequestResponseDtoMapper<StudentVerification, StudentVerificationResponseDto, StudentVerificationRequestDto> {

}
