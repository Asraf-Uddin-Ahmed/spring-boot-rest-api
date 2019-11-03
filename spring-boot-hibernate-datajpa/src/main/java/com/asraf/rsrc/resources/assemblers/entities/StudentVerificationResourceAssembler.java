package com.asraf.rsrc.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.controllers.StudentVerificationController;
import com.asraf.rsrc.dtos.mapper.StudentVerificationMapper;
import com.asraf.rsrc.entities.StudentVerification;
import com.asraf.rsrc.resources.assemblers.BaseResourceAssembler;
import com.asraf.rsrc.resources.entities.StudentVerificationResource;

@Component
public class StudentVerificationResourceAssembler
		extends BaseResourceAssembler<StudentVerification, StudentVerificationResource> {

	private final StudentVerificationMapper studentVerificationMapper;

	@Autowired
	public StudentVerificationResourceAssembler(StudentVerificationMapper studentVerificationMapper) {
		super(StudentVerificationController.class, StudentVerificationResource.class);
		this.studentVerificationMapper = studentVerificationMapper;
	}

	@Override
	public StudentVerificationResource toResource(StudentVerification entity) {
		return new StudentVerificationResource(entity, studentVerificationMapper);
	}

}