package com.asraf.dtos.response;

import com.asraf.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@JsonView(UserRoleResponse.Anonymous.class)
public class UploadFileResponseDto extends BaseResponseDto {
	private String fileName;
	private String fileType;
	private long size;
}