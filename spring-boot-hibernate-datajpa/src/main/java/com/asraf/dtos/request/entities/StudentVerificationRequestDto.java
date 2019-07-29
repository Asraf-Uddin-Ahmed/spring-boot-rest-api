package com.asraf.dtos.request.entities;

import javax.validation.constraints.NotNull;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentVerificationRequestDto extends BaseRequestDto {

	@NotNull
	private String verificationCode;

	@NotNull
	private Long studentId;

}
