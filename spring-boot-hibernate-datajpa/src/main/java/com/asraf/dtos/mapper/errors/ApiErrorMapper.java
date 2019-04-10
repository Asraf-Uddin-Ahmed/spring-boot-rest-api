package com.asraf.dtos.mapper.errors;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.asraf.dtos.response.errors.ApiErrorResponseDto;

public interface ApiErrorMapper {

	ApiErrorMapper initDefaultValidationError();

	ApiErrorMapper setStatus(HttpStatus status);

	ApiErrorMapper setMessageByErrorCode(String message);

	ApiErrorMapper setMessageByErrorCode(String errorCode, Object... params);

	ApiErrorMapper setDebugMessage(Throwable exception);

	ApiErrorMapper addValidationFieldErrors(List<FieldError> fieldErrors);

	ApiErrorMapper addValidationObjectErrors(List<ObjectError> objectErrors);

	ApiErrorMapper addValidationErrors(Set<ConstraintViolation<?>> constraintViolations);

	ApiErrorMapper addValidationErrors(Set<ConstraintViolation<Object>> violations, int row);

	ApiErrorMapper initResponseDto();

	ApiErrorResponseDto build();

}