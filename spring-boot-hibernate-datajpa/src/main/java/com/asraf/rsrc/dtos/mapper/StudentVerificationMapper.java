package com.asraf.rsrc.dtos.mapper;

import com.asraf.rsrc.dtos.request.entities.StudentVerificationRequestDto;
import com.asraf.rsrc.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.rsrc.entities.StudentVerification;

public interface StudentVerificationMapper
		extends RequestResponseDtoMapper<StudentVerification, StudentVerificationResponseDto, StudentVerificationRequestDto> {

}
