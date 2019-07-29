package com.asraf.dtos.response.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentProfileResponseDto extends BaseEntityResponseDto {

	private String address;

	private String phoneNumber;

	private StudentResponseDto student;
	
}
