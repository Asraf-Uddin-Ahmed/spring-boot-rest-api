package com.asraf.rsrc.controllers.advices;

import java.nio.file.AccessDeniedException;
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

import com.asraf.rsrc.constants.ErrorCode;
import com.asraf.rsrc.dtos.mapper.errors.ApiErrorMapper;
import com.asraf.rsrc.dtos.response.errors.ApiErrorResponseDto;
import com.asraf.rsrc.exceptions.DuplicateResourceFoundException;
import com.asraf.rsrc.exceptions.ParentDeletionException;
import com.asraf.rsrc.exceptions.RequestResourceMismatchException;
import com.asraf.rsrc.exceptions.ResourceListNotFoundException;
import com.asraf.rsrc.exceptions.ResourceNotFoundException;
import com.asraf.rsrc.utils.EnumUtils;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final ApiErrorMapper apiErrorMapper;

	@Autowired
	public RestExceptionHandler(ApiErrorMapper apiErrorMapper) {
		this.apiErrorMapper = apiErrorMapper;
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setMessageByErrorCode(ErrorCode.HttpError.MissingServletRequestParameter, ex.getParameterName())
				.setDebugMessage(ex).build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.setMessageByErrorCode(ErrorCode.HttpError.MissingServletRequestParameter, ex.getContentType(),
						ex.getSupportedMediaTypes().toString())
				.setDebugMessage(ex).build());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
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
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).setMessageByErrorCode(ErrorCode.HttpError.Message.NOT_READABLE).build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		return buildResponseEntity(this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setDebugMessage(ex).setMessageByErrorCode(ErrorCode.HttpError.Message.NOT_WRITABLE).build());
	}

	/*
	 * ExceptionHandler
	 */

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().initDefaultValidationError()
				.addValidationErrors(ex.getConstraintViolations()).build();
		return buildResponseEntity(apiError);
	}

//	@ExceptionHandler(MessagingException.class)
//	protected ResponseEntity<Object> handleMessagingException(MessagingException ex) {
//		log.error(ex.getClass().getSimpleName() + " - ", ex);
//		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//				.setMessageByErrorCode(ErrorCode.Exception.Messaging.VALUE).setDebugMessage(ex).build();
//		return buildResponseEntity(apiError);
//	}

	@ExceptionHandler(DuplicateResourceFoundException.class)
	protected ResponseEntity<Object> handleDuplicateResourceFoundException(DuplicateResourceFoundException ex) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.CONFLICT)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.DuplicateFound.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		System.out.println("################################");
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.NotFound.VALUE).setDebugMessage(ex).build();
		System.out.println(apiError.toString());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.NotFound.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.NotFound.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		if (ex.getCause() instanceof ConstraintViolationException) {
			ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
					.setMessageByErrorCode(ErrorCode.Exception.DataIntegrityViolation.CONSTRAINT_VIOLATION)
					.setDebugMessage(ex.getCause()).build();
			return buildResponseEntity(apiError);
		}
		if (ex.getCause() instanceof EntityExistsException) {
			ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.CONFLICT)
					.setMessageByErrorCode(ErrorCode.Exception.Resource.DuplicateFound.VALUE)
					.setDebugMessage(ex.getCause()).build();
			return buildResponseEntity(apiError);
		}
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setMessageByErrorCode(ErrorCode.Exception.DataIntegrityViolation.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setMessageByErrorCode(ErrorCode.Exception.MethodArgumentTypeMismatch.VALUE, ex.getName(),
						ex.getValue(), ex.getRequiredType().getSimpleName())
				.setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request)
			throws ClassNotFoundException {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setDebugMessage(ex).build();
		String message = ex.getMessage();
		if (message.contains("enum")) {
			String enumClassName = message.substring(message.lastIndexOf(' '), message.lastIndexOf('.')).trim();
			this.apiErrorMapper.setMessageByErrorCode(ErrorCode.Exception.IllegalArgument.ENUM,
					EnumUtils.getNames(enumClassName).toString());
		} else {
			this.apiErrorMapper.setMessageByErrorCode(ErrorCode.Exception.IllegalArgument.VALUE);
		}
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(AccessDeniedException ex, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.FORBIDDEN)
				.setMessageByErrorCode(ErrorCode.Exception.AccessDenied.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ParentDeletionException.class)
	protected ResponseEntity<Object> handleParentDeletionException(ParentDeletionException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.BAD_REQUEST)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.PARENT_DELETION).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ResourceListNotFoundException.class)
	protected ResponseEntity<Object> handleResourceListNotFoundException(ResourceListNotFoundException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.NOT_FOUND)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.List.NOT_FOUND).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(RequestResourceMismatchException.class)
	protected ResponseEntity<Object> handleConflictTypedException(RequestResourceMismatchException ex) {
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.CONFLICT)
				.setMessageByErrorCode(ErrorCode.Exception.Resource.RequestMismatch.VALUE).setDebugMessage(ex).build();
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		log.error(ex.getClass().getSimpleName() + " - ", ex);
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto().setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setMessageByErrorCode(ErrorCode.Exception.VALUE, ex.getClass().getSimpleName()).setDebugMessage(ex)
				.build();
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiErrorResponseDto apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}