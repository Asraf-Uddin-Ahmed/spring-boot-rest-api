package com.asraf.resources.main;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.StudentController;
import com.asraf.controllers.StudentVerificationController;
import com.asraf.entities.StudentProfile;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

public class MainResource extends BaseResource {

	public MainResource() {

		add(new ExtendedLink(linkTo(StudentController.class).withRel("students")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(StudentVerificationController.class).withRel("student-verifications"))
				.withMethod(HttpMethod.GET).withSearchableData());

		add(new ExtendedLink(linkTo(StudentProfile.class).withRel("student-profiles")).withMethod(HttpMethod.GET)
				.withSearchableData());

	}

}