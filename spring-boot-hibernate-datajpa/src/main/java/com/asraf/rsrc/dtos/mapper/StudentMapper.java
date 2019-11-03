package com.asraf.rsrc.dtos.mapper;

import com.asraf.rsrc.dtos.request.entities.StudentRequestDto;
import com.asraf.rsrc.dtos.response.entities.StudentResponseDto;
import com.asraf.rsrc.entities.Student;

public interface StudentMapper extends RequestResponseDtoMapper<Student, StudentResponseDto, StudentRequestDto> {

}
