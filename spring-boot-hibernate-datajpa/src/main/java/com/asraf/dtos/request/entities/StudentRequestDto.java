package com.asraf.dtos.request.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentRequestDto extends BaseRequestDto {
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 3, max = 10)
	private String name;
}
