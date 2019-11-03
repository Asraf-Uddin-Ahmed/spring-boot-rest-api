package com.asraf.rsrc.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpMethod;

import com.asraf.rsrc.controllers.StudentController;
import com.asraf.rsrc.controllers.StudentVerificationController;
import com.asraf.rsrc.dtos.mapper.StudentVerificationMapper;
import com.asraf.rsrc.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.entities.StudentVerification;
import com.asraf.rsrc.resources.BaseResource;
import com.asraf.rsrc.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class StudentVerificationResource extends BaseResource {

	private final StudentVerificationResponseDto studentVerification;

	public StudentVerificationResource(final StudentVerification studentVerification,
			final StudentVerificationMapper studentVerificationMapper) {

		this.studentVerification = studentVerificationMapper.getResponseDto(studentVerification);
		final long id = studentVerification.getId();
		final Student student = studentVerification.getStudent();

		add(new ExtendedLink(linkTo(methodOn(StudentVerificationController.class).getById(id)).withSelfRel())
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(StudentVerificationController.class).withRel("student-verifications"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(StudentController.class).getByEmail(student.getEmail())).withRel("student"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(StudentController.class).getByName(student.getName())).withRel("student"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(
				linkTo(methodOn(StudentController.class).getByQuery("id==" + student.getId(), null)).withRel("student"))
						.withMethod(HttpMethod.GET));

		this.loadCommonLink();

	}

	public StudentVerificationResource forDeletion() {
		super.removeLinks();
		this.loadCommonLink();
		return this;
	}

	private void loadCommonLink() {
		add(new ExtendedLink(linkTo(methodOn(StudentVerificationController.class).create(null)).withRel("create"))
				.withMethod(HttpMethod.POST));
		add(new ExtendedLink(linkTo(methodOn(StudentVerificationController.class).getRequests()).withRel("requests"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(StudentVerificationController.class).withRel("student-verifications"))
				.withMethod(HttpMethod.GET).withSearchableData());
		add(new ExtendedLink(linkTo(methodOn(StudentController.class).getByQuery(null, null)).withRel("students"))
				.withMethod(HttpMethod.GET).withSearchableData());
	}
}