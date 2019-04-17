package com.asraf.dtos.response.entities;

import java.util.Date;

import com.asraf.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserVerificationResponseDto extends BaseEntityResponseDto {

	private String verificationCode;

	@JsonView(UserRoleResponse.Admin.class)
	private Date creationTime;

	@JsonView(UserRoleResponse.Admin.class)
	private UserResponseDto User;
	
}
