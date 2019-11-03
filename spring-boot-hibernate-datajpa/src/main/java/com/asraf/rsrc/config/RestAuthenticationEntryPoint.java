package com.asraf.rsrc.config;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.constants.ContentType;
import com.asraf.rsrc.constants.ErrorCode;
import com.asraf.rsrc.dtos.mapper.errors.ApiErrorMapper;
import com.asraf.rsrc.dtos.response.errors.ApiErrorResponseDto;
import com.asraf.rsrc.services.MessageSourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ApiErrorMapper apiErrorMapper;
	
	@Autowired
	private MessageSourceService messageSourceService;

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authenticationException) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		String acceptHeader = request.getHeader("Accept");
		ContentType contentType = StringUtils.isNotBlank(acceptHeader) && ContentType.APPLICATION_XML.toString().contains(acceptHeader) 
				? ContentType.APPLICATION_XML : ContentType.APPLICATION_JSON;
		response.setContentType(contentType.toString());
		
		ApiErrorResponseDto apiError = this.apiErrorMapper.initResponseDto()
				.setStatus(HttpStatus.UNAUTHORIZED)
				.setMessageByErrorCode(ErrorCode.Exception.AccessDenied.VALUE)
				.setDebugMessage(authenticationException)
				.build();
		apiError.setMessage(this.messageSourceService.getMessage(apiError.getErrorCode(), request));
		
		Map<String, Object> data = new HashMap<>();
		data.put("apierror", apiError);
		
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = contentType == ContentType.APPLICATION_XML ? new XmlMapper() : new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		mapper.writeValue(out, data);
		out.flush();
		out.close();
	}
}