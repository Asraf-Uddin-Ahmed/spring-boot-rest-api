package com.asraf.controllers.advices;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.asraf.dtos.mapper.errors.ApiErrorMapper;
import com.asraf.dtos.response.errors.ApiErrorResponseDto;
import com.asraf.exceptions.DuplicateResourceFoundException;
import com.asraf.exceptions.ParentDeletionException;
import com.asraf.exceptions.RequestResourceMismatchException;
import com.asraf.exceptions.ResourceListNotFoundException;
import com.asraf.exceptions.ResourceNotFoundException;
import com.asraf.utils.EnumUtils;

import lombok.extern.log4j.Log4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final ApiErrorMapper apiErrorMapper;

	@Autowired
	public RestExceptionHandler(ApiErrorMapper apiErrorMapper) {
		this.apiErrorMapper = apiErrorMapper;
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).setMessage(error).build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.setDebugMessage(ex).setMessage(builder.substring(0, builder.length() - 2)).build());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().initDefaultValidationError()
				.addValidationFieldErrors(ex.getBindingResult().getFieldErrors())
				.addValidationObjectErrors(ex.getBindingResult().getGlobalErrors()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		log.info(String.format("%s to %s", servletWebRequest.getHttpMethod().toString(),
				servletWebRequest.getRequest().getServletPath().toString()));
		String error = "Malformed JSON request";
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).setMessage(error).build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error writing JSON output";
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setDebugMessage(ex).setMessage(error).build());
	}

	/*
	 * ExceptionHandler
	 */

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().initDefaultValidationError()
				.addValidationErrors(ex.getConstraintViolations()).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessage(ex.getMessage()).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessage(ex.getMessage()).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		if (ex.getCause() instanceof ConstraintViolationException) {
			ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
					.setMessage("Operation cannot be performed. Integrity Constraint violated")
					.setDebugMessage(ex.getCause()).build();
			return buildResponseEntity(apiError);
		}
		if (ex.getCause() instanceof EntityExistsException) {
			ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.CONFLICT)
					.setMessage("Resource already exists").setDebugMessage(ex.getCause()).build();
			return buildResponseEntity(apiError);
		}
		log.error(ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setMessage(message).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request)
			throws ClassNotFoundException {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).build();
		String message = ex.getMessage();
		if (message.contains("enum")) {
			String enumClassName = message.substring(message.lastIndexOf(' '), message.lastIndexOf('.')).trim();
			this.apiErrorMapper
					.setMessage(String.format("Enum value must be: %s", EnumUtils.getNames(enumClassName).toString()));
		}
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ParentDeletionException.class)
	protected ResponseEntity<Object> handleParentDeletionException(ParentDeletionException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setMessage(ex.getMessage()).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ResourceListNotFoundException.class)
	protected ResponseEntity<Object> handleResourceListNotFoundException(ResourceListNotFoundException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessage(ex.getMessage()).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({DuplicateResourceFoundException.class, RequestResourceMismatchException.class})
	protected ResponseEntity<Object> handleConflictTypedException(RuntimeException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.CONFLICT)
				.setMessage(ex.getMessage()).build();
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		log.error(ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).setMessage("ExceptionHandler is not defined for: " + ex.getClass()).build();
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiErrorResponseDto apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}