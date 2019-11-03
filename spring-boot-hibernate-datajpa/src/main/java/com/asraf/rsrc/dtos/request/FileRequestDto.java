package com.asraf.rsrc.dtos.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class FileRequestDto extends BaseRequestDto {

	@NotBlank
	private String publicUrl;

	@NotBlank
	private String filePath;

	@NotBlank
	private String originalName;

}
