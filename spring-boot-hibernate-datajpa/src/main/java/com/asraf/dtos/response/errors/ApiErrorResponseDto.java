package com.asraf.dtos.response.errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.asraf.dtos.response.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@JsonInclude(Include.NON_EMPTY)
public class ApiErrorResponseDto extends BaseResponseDto {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String errorCode;
	private String message;
	private String debugMessage;
	private List<ApiSubErrorResponseDto> subErrors;

	public ApiErrorResponseDto() {
		this.subErrors = new ArrayList<>();
		this.timestamp = LocalDateTime.now();
	}

	public boolean hasSubErrors() {
		return subErrors != null && !subErrors.isEmpty();
	}

	public void addValidationError(ApiSubErrorResponseDto apiSubErrorResponseDto) {
		this.subErrors.add(apiSubErrorResponseDto);
	}

	public void addValidationErrors(Collection<ApiSubErrorResponseDto> apiSubErrorResponseDtos) {
		this.subErrors.addAll(apiSubErrorResponseDtos);
	}

}