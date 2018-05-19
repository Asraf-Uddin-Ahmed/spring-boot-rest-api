package com.asraf.core.dtos.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRequestDto extends BaseRequestDto {
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 3, max = 10)
	private String name;
}
