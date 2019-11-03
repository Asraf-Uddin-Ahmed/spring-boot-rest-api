package com.asraf.rsrc.dtos.request.filestorage;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.asraf.rsrc.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FilesObjectRequestDto extends BaseRequestDto {
	@NotEmpty
	private List<@NotBlank String> filePaths;
}
