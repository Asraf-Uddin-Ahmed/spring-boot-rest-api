package com.asraf.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.UserController;
import com.asraf.controllers.UserVerificationController;
import com.asraf.dtos.mapper.UserVerificationMapper;
import com.asraf.dtos.response.entities.UserVerificationResponseDto;
import com.asraf.entities.UserEntity;
import com.asraf.entities.UserVerification;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class UserVerificationResource extends BaseResource {

	private final UserVerificationResponseDto userVerification;

	public UserVerificationResource(final UserVerification userVerification,
			final UserVerificationMapper userVerificationMapper) {

		this.userVerification = userVerificationMapper.getResponseDto(userVerification);
		final long id = userVerification.getId();
		final UserEntity user = userVerification.getUser();

		add(new ExtendedLink(linkTo(methodOn(UserVerificationController.class).getById(id)).withSelfRel())
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(UserVerificationController.class).withRel("user-verifications"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(UserController.class).getByEmail(user.getEmail())).withRel("user"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(UserController.class).getByName(user.getName())).withRel("user"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(
				linkTo(methodOn(UserController.class).getByQuery("id==" + user.getId(), null)).withRel("user"))
						.withMethod(HttpMethod.GET));

		this.loadCommonLink();

	}

	public UserVerificationResource forDeletion() {
		super.removeLinks();
		this.loadCommonLink();
		return this;
	}

	private void loadCommonLink() {
		add(new ExtendedLink(linkTo(methodOn(UserVerificationController.class).create(null)).withRel("create"))
				.withMethod(HttpMethod.POST));
		add(new ExtendedLink(linkTo(methodOn(UserVerificationController.class).getRequests()).withRel("requests"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(UserVerificationController.class).withRel("user-verifications"))
				.withMethod(HttpMethod.GET).withSearchableData());
		add(new ExtendedLink(linkTo(methodOn(UserController.class).getByQuery(null, null)).withRel("users"))
				.withMethod(HttpMethod.GET).withSearchableData());
	}
}