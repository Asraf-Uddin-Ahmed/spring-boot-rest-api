package com.asraf.dtos.response.requestdto;

import com.asraf.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_EMPTY)
@JsonView(UserRoleResponse.Anonymous.class)
public class RequestField {
	private String name;
	private String type;
	private String parameterizedType;
	private String value;
	private FieldValidations validations;
}
