package com.asraf.core.dtos.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfileResponseDto extends BaseResponseDto {

	private String address;

	private String phoneNumber;

	private UserResponseDto User;
	
}
