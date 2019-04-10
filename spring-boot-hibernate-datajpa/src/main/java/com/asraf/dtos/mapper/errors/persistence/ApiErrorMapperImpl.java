package com.asraf.dtos.mapper.errors.persistence;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.asraf.constants.ErrorCode;
import com.asraf.dtos.mapper.errors.ApiErrorMapper;
import com.asraf.dtos.mapper.errors.ApiValidationErrorMapper;
import com.asraf.dtos.mapper.errors.CsvValidationErrorMapper;
import com.asraf.dtos.mapper.persistence.DtoMapperImpl;
import com.asraf.dtos.response.errors.ApiErrorResponseDto;
import com.asraf.dtos.response.errors.CsvValidationErrorResponseDto;
import com.asraf.services.MessageSourceService;

@Component
@Scope(value = "prototype")
public class ApiErrorMapperImpl extends DtoMapperImpl implements ApiErrorMapper {

	private ApiErrorResponseDto apiErrorResponseDto;

	private final ApiValidationErrorMapper apiValidationErrorMapper;
	private final CsvValidationErrorMapper csvValidationErrorMapper;
	private final MessageSourceService messageSourceService;

	@Autowired
	protected ApiErrorMapperImpl(ModelMapper modelMapper, ApiValidationErrorMapper apiValidationErrorMapper,
			MessageSourceService messageSourceService, CsvValidationErrorMapper csvValidationErrorMapper) {
		super(modelMapper);
		this.apiValidationErrorMapper = apiValidationErrorMapper;
		this.csvValidationErrorMapper = csvValidationErrorMapper;
		this.messageSourceService = messageSourceService;
	}

	public ApiErrorMapper initDefaultValidationError() {
		this.apiErrorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
		this.apiErrorResponseDto.setErrorCode(ErrorCode.Validation.ERROR);
		this.apiErrorResponseDto.setMessage(messageSourceService.getMessage(ErrorCode.Validation.ERROR));
		return this;
	}

	public ApiErrorMapper setStatus(HttpStatus status) {
		this.apiErrorResponseDto.setStatus(status);
		return this;
	}

	public ApiErrorMapper setMessageByErrorCode(String errorCode) {
		this.apiErrorResponseDto.setErrorCode(errorCode);
		this.apiErrorResponseDto.setMessage(messageSourceService.getMessage(errorCode));
		return this;
	}

	public ApiErrorMapper setMessageByErrorCode(String errorCode, Object... params) {
		this.apiErrorResponseDto.setErrorCode(errorCode);
		this.apiErrorResponseDto.setMessage(String.format(messageSourceService.getMessage(errorCode), params));
		return this;
	}

	public ApiErrorMapper setDebugMessage(Throwable exception) {
		this.apiErrorResponseDto.setDebugMessage(exception.getLocalizedMessage());
		return this;
	}

	public ApiErrorMapper addValidationFieldErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
		return this;
	}

	public ApiErrorMapper addValidationObjectErrors(List<ObjectError> objectErrors) {
		objectErrors.forEach(this::addValidationError);
		return this;
	}

	public ApiErrorMapper addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		this.apiValidationErrorMapper.getApiValidationErrors(constraintViolations)
				.forEach(ave -> this.apiErrorResponseDto.addValidationError(ave));
		return this;
	}

	public ApiErrorMapper addValidationErrors(Set<ConstraintViolation<Object>> constraintViolations, int row) {
		CsvValidationErrorResponseDto csvValidationErrorResponseDto = this.csvValidationErrorMapper
				.addValidationErrors(constraintViolations, row);
		if (csvValidationErrorResponseDto != null) {
			this.apiErrorResponseDto.addValidationError(csvValidationErrorResponseDto);
		}
		return this;
	}

	public ApiErrorMapper initResponseDto() {
		this.apiErrorResponseDto = new ApiErrorResponseDto();
		return this;
	}

	public ApiErrorResponseDto build() {
		return this.apiErrorResponseDto;
	}

	private void addValidationError(FieldError fieldError) {
		this.apiErrorResponseDto.addValidationError(this.apiValidationErrorMapper.getApiValidationError(fieldError));
	}

	private void addValidationError(ObjectError objectError) {
		this.apiErrorResponseDto.addValidationError(this.apiValidationErrorMapper.getApiValidationError(objectError));
	}

}
