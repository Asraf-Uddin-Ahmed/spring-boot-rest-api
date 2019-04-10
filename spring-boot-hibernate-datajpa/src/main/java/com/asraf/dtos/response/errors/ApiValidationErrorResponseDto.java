package com.asraf.dtos.response.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiValidationErrorResponseDto extends ApiSubErrorResponseDto {
	private String object;
	private String field;
	private Object rejectedValue;
	private String errorCode;
	private String message;

	public ApiValidationErrorResponseDto(String object, String errorCode, String message) {
		this.object = object;
		this.errorCode = errorCode;
		this.message = message;
	}
}