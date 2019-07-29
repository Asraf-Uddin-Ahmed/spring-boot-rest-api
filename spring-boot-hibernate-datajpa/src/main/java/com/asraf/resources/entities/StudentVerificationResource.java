package com.asraf.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.StudentController;
import com.asraf.controllers.StudentVerificationController;
import com.asraf.dtos.mapper.StudentVerificationMapper;
import com.asraf.dtos.response.entities.StudentVerificationResponseDto;
import com.asraf.entities.Student;
import com.asraf.entities.StudentVerification;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

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