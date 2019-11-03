package com.asraf.rsrc.dtos.response.requestdto;

import java.util.List;
import java.util.Map;

import com.asraf.rsrc.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
@JsonView(UserRoleResponse.Anonymous.class)
public class FieldValidations {
	private Boolean isRequired;
	private Boolean isEmail;
	private Boolean isMobileNumber;
	private Long minValue;
	private Long maxValue;
	private Integer minSize;
	private Integer maxSize;
	private String pattern;
	private Map<String, Object> options;
	private List<FieldConditionalValidation> fieldConditionalValidations;
}
