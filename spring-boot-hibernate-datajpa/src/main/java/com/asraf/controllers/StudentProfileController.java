package com.asraf.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.StudentProfileMapper;
import com.asraf.dtos.request.entities.StudentProfileRequestDto;
import com.asraf.dtos.response.entities.StudentProfileResponseDto;
import com.asraf.entities.Student;
import com.asraf.entities.StudentProfile;
import com.asraf.services.StudentProfileService;
import com.asraf.services.StudentService;

@RestController
@RequestMapping("")
public class StudentProfileController {

	private StudentProfileService studentProfileService;
	private StudentService studentService;
	private StudentProfileMapper studentProfileMappper;

	@Autowired
	public StudentProfileController(StudentProfileService studentProfileService, StudentService studentService,
			StudentProfileMapper studentProfileMappper) {
		this.studentProfileMappper = studentProfileMappper;
		this.studentService = studentService;
		this.studentProfileService = studentProfileService;
	}

	@GetMapping("/student-profiles")
	public List<StudentProfileResponseDto> getAll() {
		List<StudentProfileResponseDto> response = studentProfileMappper.getResponseDtos(this.studentProfileService.getAll());
		return response;
	}

	@GetMapping("/student-profiles/{id}")
	public StudentProfileResponseDto getById(@PathVariable long id) {
		StudentProfile studentProfile = studentProfileService.getById(id);
		return studentProfileMappper.getResponseDto(studentProfile);
	}

	@PostMapping("students/{studentId}/student-profiles")
	@ResponseStatus(HttpStatus.CREATED)
	public StudentProfileResponseDto create(@PathVariable long studentId,
			@Valid @RequestBody StudentProfileRequestDto requestDto) {
		Student student = this.studentService.getById(studentId);
		StudentProfile studentProfile = studentProfileMappper.getEntity(requestDto, student);
		studentProfileService.save(studentProfile);
		return studentProfileMappper.getResponseDto(studentProfile);
	}

	@DeleteMapping("/student-profiles/{id}")
	public StudentProfileResponseDto delete(@PathVariable long id) {
		StudentProfile studentProfile = studentProfileService.getById(id);
		StudentProfileResponseDto response = studentProfileMappper.getResponseDto(studentProfile);
		studentProfileService.delete(studentProfile);
		return response;
	}

	@PutMapping("/student-profiles/{id}")
	public StudentProfileResponseDto update(@PathVariable long id, @Valid @RequestBody StudentProfileRequestDto requestDto) {
		StudentProfile studentProfile = studentProfileService.getById(id);
		studentProfileMappper.loadEntity(requestDto, studentProfile);
		studentProfileService.save(studentProfile);
		return studentProfileMappper.getResponseDto(studentProfile);
	}

}
