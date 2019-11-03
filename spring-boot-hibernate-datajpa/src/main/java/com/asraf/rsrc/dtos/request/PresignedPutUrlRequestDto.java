package com.asraf.rsrc.dtos.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PresignedPutUrlRequestDto extends BaseRequestDto {

	@NotBlank
	private String fileExtension;

	private Integer expirationInMinute;

}
