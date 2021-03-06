package com.asraf.rsrc.dtos.request.entities;

import javax.validation.constraints.NotNull;

import com.asraf.rsrc.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentProfileRequestDto extends BaseRequestDto {

	@NotNull
	private String address;

	@NotNull
	private String phoneNumber;

}
