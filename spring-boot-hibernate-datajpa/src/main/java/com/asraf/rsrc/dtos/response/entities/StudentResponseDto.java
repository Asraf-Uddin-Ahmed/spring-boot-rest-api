package com.asraf.rsrc.dtos.response.entities;

import java.util.List;

import com.asraf.rsrc.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentResponseDto extends BaseEntityResponseDto {
	private String email;
	private String name;
	
	// TODO: unable to show usreProfile in response because student entity has not studentProfile
	// private StudentProfileResponseDto studentProfile;
	
	@JsonView(UserRoleResponse.Admin.class)
	private List<StudentVerificationResponseDto> studentVerifications;
}
