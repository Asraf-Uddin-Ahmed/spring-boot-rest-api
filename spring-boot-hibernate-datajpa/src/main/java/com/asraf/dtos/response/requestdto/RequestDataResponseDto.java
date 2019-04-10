package com.asraf.dtos.response.requestdto;

import org.springframework.http.HttpMethod;

import com.asraf.dtos.request.BaseRequestDto;
import com.asraf.dtos.response.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@JsonInclude(Include.NON_NULL)
public class RequestDataResponseDto extends BaseResponseDto {

	private String uri;
	private HttpMethod method;
	private RequestBodyResponseDto<? extends BaseRequestDto> body;

}
