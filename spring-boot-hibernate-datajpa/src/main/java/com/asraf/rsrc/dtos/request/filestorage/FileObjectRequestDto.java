package com.asraf.rsrc.dtos.request.filestorage;

import javax.validation.constraints.NotBlank;

import com.asraf.rsrc.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileObjectRequestDto extends BaseRequestDto {
	@NotBlank
	private String filePath;
}
