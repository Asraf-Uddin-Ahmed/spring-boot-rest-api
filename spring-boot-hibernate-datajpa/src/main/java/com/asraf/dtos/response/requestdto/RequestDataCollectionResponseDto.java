package com.asraf.dtos.response.requestdto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;

import com.asraf.dtos.request.BaseRequestDto;
import com.asraf.dtos.response.BaseResponseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestDataCollectionResponseDto extends BaseResponseDto {

	private List<RequestDataResponseDto> requests;

	public RequestDataCollectionResponseDto() {
		this.requests = new ArrayList<>();
	}

	public RequestDataCollectionResponseDto addRequest(RequestDataResponseDto request) {
		this.requests.add(request);
		return this;
	}

	public RequestDataCollectionResponseDto addRequest(URI uri, HttpMethod method,
			RequestBodyResponseDto<? extends BaseRequestDto> requestBody) {
		this.requests
				.add(RequestDataResponseDto.builder().uri(uri.toString()).method(method).body(requestBody).build());
		return this;
	}

	public RequestDataCollectionResponseDto addRequest(ControllerLinkBuilder uri, HttpMethod method,
			RequestBodyResponseDto<? extends BaseRequestDto> requestBody) {
		this.requests
				.add(RequestDataResponseDto.builder().uri(uri.toString()).method(method).body(requestBody).build());
		return this;
	}

}
