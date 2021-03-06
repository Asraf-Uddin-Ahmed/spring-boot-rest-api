package com.asraf.rsrc.dtos.response.entities;

import java.util.Date;

import com.asraf.rsrc.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentVerificationResponseDto extends BaseEntityResponseDto {

	private String verificationCode;

	@JsonView(UserRoleResponse.Admin.class)
	private Date creationTime;

	@JsonView(UserRoleResponse.Admin.class)
	private StudentResponseDto student;
	
}
