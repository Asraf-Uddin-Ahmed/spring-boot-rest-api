package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.StudentRequestDto;
import com.asraf.dtos.response.entities.StudentResponseDto;
import com.asraf.entities.Student;

public interface StudentMapper extends RequestResponseDtoMapper<Student, StudentResponseDto, StudentRequestDto> {

}
