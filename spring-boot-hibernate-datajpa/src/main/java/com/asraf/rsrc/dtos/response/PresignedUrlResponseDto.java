package com.asraf.rsrc.dtos.response;

import java.util.Date;

import com.asraf.rsrc.constants.UserRoleResponse;
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
public class PresignedUrlResponseDto extends BaseResponseDto {
	private String presignedUrl;
	private String filePath;
	private String publicUrl;
	private Date expirationTime;
}
